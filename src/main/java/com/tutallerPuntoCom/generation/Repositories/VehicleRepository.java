package com.tutallerPuntoCom.generation.Repositories;


import com.tutallerPuntoCom.generation.Models.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    boolean existsByPlateIgnoreCase(String plate);
    Page<Vehicle> findByUserId(Long userId, Pageable pageable);
}