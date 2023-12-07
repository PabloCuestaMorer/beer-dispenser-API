package com.rviewer.skeletons.domain.repository;

import com.rviewer.skeletons.domain.model.Dispenser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DispenserRepository extends JpaRepository<Dispenser, Long> {
    // You can add custom methods if required
}
