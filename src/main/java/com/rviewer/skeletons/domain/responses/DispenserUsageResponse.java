package com.rviewer.skeletons.domain.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DispenserUsageResponse {
    private LocalDateTime openedAt;
    private LocalDateTime closedAt;
    private double flowVolume;
    private double totalSpent;

    // Constructor, getters, and setters
}
