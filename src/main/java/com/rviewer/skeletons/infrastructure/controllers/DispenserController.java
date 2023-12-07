package com.rviewer.skeletons.infrastructure.controllers;

import com.rviewer.skeletons.domain.requests.CreateDispenserRequest;
import com.rviewer.skeletons.domain.requests.UpdateDispenserStatusRequest;
import com.rviewer.skeletons.domain.responses.CreateDispenserResponse;
import com.rviewer.skeletons.domain.responses.DispenserSpendingResponse;
import com.rviewer.skeletons.domain.services.DispenserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dispenser")
public class DispenserController {

    private final DispenserService dispenserService;

    public DispenserController(DispenserService dispenserService) {
        this.dispenserService = dispenserService;
    }

    @PostMapping
    public ResponseEntity<CreateDispenserResponse> createDispenser(@RequestBody CreateDispenserRequest request) {
        CreateDispenserResponse response = dispenserService.createDispenser(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateDispenserStatus(@PathVariable Long id, @RequestBody UpdateDispenserStatusRequest request) {
        return dispenserService.updateStatus(id, request);
    }

    @GetMapping("/{id}/spending")
    public ResponseEntity<DispenserSpendingResponse> getSpending(@PathVariable Long id) {
        DispenserSpendingResponse response = dispenserService.calculateSpending(id);
        return ResponseEntity.ok(response);
    }

}
