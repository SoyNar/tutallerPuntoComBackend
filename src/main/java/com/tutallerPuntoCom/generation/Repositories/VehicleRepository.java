package com.tutallerPuntoCom.generation.Repositories;

import com.tutallerPuntoCom.generation.Models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
