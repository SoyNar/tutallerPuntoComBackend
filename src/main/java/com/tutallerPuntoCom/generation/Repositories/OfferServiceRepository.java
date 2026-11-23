package com.tutallerPuntoCom.generation.Repositories;

import com.tutallerPuntoCom.generation.Models.OfferService;
import com.tutallerPuntoCom.generation.Utils.Enums.CategoryServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OfferServiceRepository extends JpaRepository<OfferService, Long> {
    List<OfferService> findByActiveTrue();
    List<OfferService> findByActiveTrueAndCategoryServices(CategoryServices categoryServices);
    Optional<OfferService> findByIdAndActiveTrue(Long id);
}