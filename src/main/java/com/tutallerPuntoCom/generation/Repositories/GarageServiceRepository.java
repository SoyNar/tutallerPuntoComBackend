package com.tutallerPuntoCom.generation.Repositories;

import com.tutallerPuntoCom.generation.Models.GarageService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GarageServiceRepository extends JpaRepository<GarageService, Long> {

    Optional<GarageService> findByGarageIdAndOfferServiceId(Long garageId, Long serviceId);
}
