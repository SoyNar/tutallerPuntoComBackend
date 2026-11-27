package com.tutallerPuntoCom.generation.Repositories;

import com.tutallerPuntoCom.generation.Models.GarageService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
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

    // Servicios activos de varios talleres al mismo tiempo
    @Query("""
            SELECT gs FROM GarageService gs
            JOIN FETCH gs.offerService s
            WHERE gs.garage.id IN :garageIds
              AND s.active = true
            ORDER BY s.name
            """)
    List<GarageService> findActiveByGarageIds(@Param("garageIds") Collection<Long> garageIds);

    // Borrado masivo para los servicios de un taller
    @Modifying
    @Query("DELETE FROM GarageService gs WHERE gs.garage.id = :garageId")
    void deleteByGarageId(@Param("garageId") Long garageId);
}
