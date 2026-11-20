package com.tutallerPuntoCom.generation.Repositories;

import com.tutallerPuntoCom.generation.Models.OfferService;
import com.tutallerPuntoCom.generation.Utils.Enums.CategoryServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferServiceRepository extends JpaRepository<OfferService, Long> {

    List<OfferService> findByCategoryServices(CategoryServices categoryServices);

    boolean existsByNameIgnoreCase(String name);
}
