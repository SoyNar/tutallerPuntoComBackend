package com.tutallerPuntoCom.generation.Repositories;

import com.tutallerPuntoCom.generation.Models.OfferService;
import com.tutallerPuntoCom.generation.Utils.Enums.CategoryServices;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface OfferServiceRepository extends JpaRepository<OfferService, Long> {
    Page<OfferService> findByActiveTrue(Pageable pageable);
    Page<OfferService> findByActiveTrueAndCategoryServices(CategoryServices categoryServices, Pageable pageable);
    Optional<OfferService> findByIdAndActiveTrue(Long id);
}