package com.rviewer.skeletons.infrastructure.services;

import com.rviewer.skeletons.domain.model.Dispenser;
import com.rviewer.skeletons.domain.model.DispenserUsage;
import com.rviewer.skeletons.domain.repository.DispenserRepository;
import com.rviewer.skeletons.domain.requests.CreateDispenserRequest;
import com.rviewer.skeletons.domain.requests.UpdateDispenserStatusRequest;
import com.rviewer.skeletons.domain.responses.DispenserSpendingResponse;
import com.rviewer.skeletons.domain.services.DispenserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DispenserServiceTest {

    @Mock
    private DispenserRepository dispenserRepository;

    @InjectMocks
    private DispenserService dispenserService;

    private Dispenser dispenser;

    @BeforeEach
    void setUp() {
        dispenser = new Dispenser();
        dispenser.setId(1L);
        dispenser.setFlowVolume(0.0653);
        dispenser.setStatus("close");
        dispenser.setUsages(new ArrayList<>());
    }


    @Test
    void createDispenser_ShouldCreateDispenser() {
        CreateDispenserRequest request = new CreateDispenserRequest();
        request.setFlowVolume(0.0653);

        when(dispenserRepository.save(any(Dispenser.class))).thenReturn(dispenser);

        var response = dispenserService.createDispenser(request);

        assertThat(response.getFlowVolume()).isEqualTo(request.getFlowVolume());
        verify(dispenserRepository).save(any(Dispenser.class));
    }

    @Test
    void updateStatus_ShouldOpenDispenser() {
        UpdateDispenserStatusRequest request = new UpdateDispenserStatusRequest();
        request.setStatus("open");
        request.setUpdatedAt(LocalDateTime.now());

        when(dispenserRepository.findById(anyLong())).thenReturn(Optional.of(dispenser));
        when(dispenserRepository.save(any(Dispenser.class))).thenReturn(dispenser);

        ResponseEntity<?> response = dispenserService.updateStatus(1L, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(dispenser.getStatus()).isEqualTo("open");
    }

    @Test
    void updateStatus_ShouldCloseDispenser() {
        dispenser.setStatus("open");
        UpdateDispenserStatusRequest request = new UpdateDispenserStatusRequest();
        request.setStatus("close");
        request.setUpdatedAt(LocalDateTime.now());

        when(dispenserRepository.findById(anyLong())).thenReturn(Optional.of(dispenser));
        when(dispenserRepository.save(any(Dispenser.class))).thenReturn(dispenser);

        ResponseEntity<?> response = dispenserService.updateStatus(1L, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(dispenser.getStatus()).isEqualTo("close");
    }

    @Test
    void calculateSpending_ShouldCalculateTotalSpent() {
        DispenserUsage usage = new DispenserUsage();
        usage.setOpenedAt(LocalDateTime.now().minusSeconds(60)); // Opened 60 seconds ago
        usage.setFlowVolume(0.0653);
        dispenser.getUsages().add(usage);

        when(dispenserRepository.findById(anyLong())).thenReturn(Optional.of(dispenser));

        DispenserSpendingResponse response = dispenserService.calculateSpending(1L);

        assertThat(response.getTotalSpent()).isGreaterThan(0.0);
    }

}
