package com.tutallerPuntoCom.generation.DTos.ResponseDTo;


import com.tutallerPuntoCom.generation.Models.OfferService;
import com.tutallerPuntoCom.generation.Utils.Enums.CategoryServices;
import java.math.BigDecimal;
import java.time.Instant;

public record ServiceResponseDTO(
        Long id,
        String name,
        CategoryServices categoryServices,
        BigDecimal price,
        Boolean active,
        Instant createdAt
) {
    public static ServiceResponseDTO from(OfferService service) {
        return new ServiceResponseDTO(
                service.getId(),
                service.getName(),
                service.getCategoryServices(),
                service.getPrice(),
                service.getActive(),
                service.getCreatedAt()
        );
    }
}