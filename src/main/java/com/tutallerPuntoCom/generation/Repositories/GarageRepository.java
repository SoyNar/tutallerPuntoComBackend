package com.tutallerPuntoCom.generation.Repositories;

import com.tutallerPuntoCom.generation.Models.Garage;
import com.tutallerPuntoCom.generation.Utils.Enums.GarageStatus;
import com.tutallerPuntoCom.generation.Utils.Enums.TypeVehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GarageRepository extends JpaRepository<Garage, Long> {

    //Contrato

    // Validar si ya existe el correo ingresado
    boolean existsByEmail (String email);

    // Consultas para el filtro
    @Query("""
            SELECT g FROM Garage g
            WHERE g.garageStatus = :status
              AND (:city IS NULL OR LOWER(g.city) = LOWER(:city))
              AND (:vehicleType IS NULL
                   OR g.typeVehicle = :vehicleType
                   OR g.typeVehicle = com.tutallerPuntoCom.generation.Utils.Enums.TypeVehicle.AMBOS)
              AND (:serviceId IS NULL OR EXISTS (
                   SELECT gs.id FROM GarageService gs
                   WHERE gs.garage = g AND gs.offerService.id = :serviceId))
            """)
    Page<Garage> search(
            @Param(value = "status")GarageStatus status,
            @Param("city") String city,
            @Param("vehicleType")TypeVehicle vehicleType,
            Pageable pageable
            );
}
