package com.rviewer.skeletons.domain.responses;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DispenserSpendingResponse {
    private double totalSpent;
    private List<DispenserUsageResponse> usages;

    public DispenserSpendingResponse(double totalSpent, List<DispenserUsageResponse> usages) {
        this.totalSpent = totalSpent;
        this.usages = usages;
    }

    // Getters and setters
}

