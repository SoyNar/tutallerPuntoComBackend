
package com.tutallerPuntoCom.generation.Repositories;

import com.tutallerPuntoCom.generation.Models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    boolean existsByPlateIgnoreCase(String plate);

    Optional<Vehicle> findByPlateIgnoreCase(String plate);

    List<Vehicle> findByUserId(Long userId);

}
