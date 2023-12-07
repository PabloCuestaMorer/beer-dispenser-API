package com.rviewer.skeletons.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class DispenserUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dispenser_id", nullable = false)
    private Dispenser dispenser;

    private LocalDateTime openedAt;
    private LocalDateTime closedAt;
    private double flowVolume;

    // Standard getters and setters
}

