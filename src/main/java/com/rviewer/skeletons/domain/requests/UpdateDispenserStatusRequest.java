package com.rviewer.skeletons.domain.requests;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateDispenserStatusRequest {
    private String status; // "open" or "close"
    private LocalDateTime updatedAt;

    // Getters and Setters
}
