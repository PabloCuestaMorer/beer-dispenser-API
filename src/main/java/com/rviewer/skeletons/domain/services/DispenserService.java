package com.rviewer.skeletons.domain.services;

import com.rviewer.skeletons.domain.model.Dispenser;
import com.rviewer.skeletons.domain.model.DispenserUsage;
import com.rviewer.skeletons.domain.repository.DispenserRepository;
import com.rviewer.skeletons.domain.requests.CreateDispenserRequest;
import com.rviewer.skeletons.domain.requests.UpdateDispenserStatusRequest;
import com.rviewer.skeletons.domain.responses.CreateDispenserResponse;
import com.rviewer.skeletons.domain.responses.DispenserSpendingResponse;
import com.rviewer.skeletons.domain.responses.DispenserUsageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DispenserService {

    private final DispenserRepository dispenserRepository;

    @Autowired
    public DispenserService(DispenserRepository dispenserRepository) {
        this.dispenserRepository = dispenserRepository;
    }

    public CreateDispenserResponse createDispenser(CreateDispenserRequest request) {
        Dispenser dispenser = new Dispenser();
        dispenser.setFlowVolume(request.getFlowVolume());
        dispenser.setStatus("close");
        dispenser = dispenserRepository.save(dispenser);
        return new CreateDispenserResponse(dispenser.getId().toString(), dispenser.getFlowVolume());
    }


    public ResponseEntity<?> updateStatus(Long id, UpdateDispenserStatusRequest request) {
        Optional<Dispenser> existingDispenser = dispenserRepository.findById(id);
        if (existingDispenser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected API error");
        }

        Dispenser dispenser = existingDispenser.get();
        if (dispenser.getStatus().equals(request.getStatus())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Dispenser is already " + request.getStatus());
        }

        // Create a new usage record when opening the dispenser
        if ("open".equals(request.getStatus())) {
            dispenser.setStatus(request.getStatus());
            dispenser.setStartTime(LocalDateTime.now());

            DispenserUsage usage = new DispenserUsage();
            usage.setDispenser(dispenser);
            usage.setOpenedAt(request.getUpdatedAt());
            usage.setFlowVolume(dispenser.getFlowVolume());
            dispenser.getUsages().add(usage);
        } else if ("close".equals(request.getStatus())) {
            dispenser.setStatus(request.getStatus());
            dispenser.setEndTime(request.getUpdatedAt());

            DispenserUsage lastUsage = findLastOpenedUsage(dispenser.getUsages());
            if (lastUsage != null) {
                lastUsage.setClosedAt(request.getUpdatedAt());
                double usageCost = calculateTotalSpent(lastUsage);
                dispenser.setTotalCost(dispenser.getTotalCost() + usageCost);
            }
        }

        dispenserRepository.save(dispenser);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Status of the tap changed correctly");
    }

    private DispenserUsage findLastOpenedUsage(List<DispenserUsage> usages) {
        return usages.stream()
                .filter(usage -> usage.getOpenedAt() != null && usage.getClosedAt() == null)
                .reduce((first, second) -> second)
                .orElse(null);
    }

    private static final double COST_PER_LITER = 12.25; // Cost per liter in euros

    public DispenserSpendingResponse calculateSpending(Long id) {
        Dispenser dispenser = dispenserRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dispenser not found"));

        List<DispenserUsageResponse> usageResponses = dispenser.getUsages().stream()
                .map(this::createUsageResponse)
                .collect(Collectors.toList());

        double totalAmount = usageResponses.stream()
                .mapToDouble(DispenserUsageResponse::getTotalSpent)
                .sum();

        return new DispenserSpendingResponse(totalAmount, usageResponses);
    }

    private DispenserUsageResponse createUsageResponse(DispenserUsage usage) {
        double totalSpent = calculateTotalSpent(usage);
        return new DispenserUsageResponse(usage.getOpenedAt(), usage.getClosedAt(), usage.getFlowVolume(), totalSpent);
    }

    private double calculateTotalSpent(DispenserUsage usage) {
        if (usage.getOpenedAt() == null) {
            return 0.0;
        }

        LocalDateTime endTime = (usage.getClosedAt() != null) ? usage.getClosedAt() : LocalDateTime.now();
        long durationInSeconds = Duration.between(usage.getOpenedAt(), endTime).getSeconds();
        double totalLiters = durationInSeconds * usage.getFlowVolume();
        return totalLiters * COST_PER_LITER;
    }



}
