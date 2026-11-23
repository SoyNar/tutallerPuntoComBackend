package com.tutallerPuntoCom.generation.Repositories;

import com.tutallerPuntoCom.generation.Models.GarageService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GarageServiceRepository extends JpaRepository<GarageService, Long> {

    Optional<GarageService> findByGarageIdAndOfferServiceId(Long garageId, Long serviceId);

    // Trae el servicio del catalogo en una sola consulta
    @Query("""
            SELECT gs FROM GarageService gs
            JOIN FETCH gs.offerService s
            WHERE gs.garage.id = :garageId
            ORDER BY s.name
            """)
    List<GarageService> findByGarageIdWithService(@Param("garageId") Long garageId);
}
