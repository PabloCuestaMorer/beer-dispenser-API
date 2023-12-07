package com.rviewer.skeletons.domain.model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Dispenser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dispenser_generator")
    @SequenceGenerator(
            name = "dispenser_generator",
            sequenceName = "dispenser_seq",
            allocationSize = 1
    )
    private Long id;

    private double flowVolume;
    private String status; // Consider using an Enum here for 'open' and 'close' values.
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double totalCost;

    @OneToMany(mappedBy = "dispenser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DispenserUsage> usages = new ArrayList<>();


    // Getters, setters, and other methods
}

