package com.tutallerPuntoCom.generation.DTos.ResponseDTo;

import com.tutallerPuntoCom.generation.Models.GarageService;

import java.math.BigDecimal;

public record ServiceSummaryResponseDTO(Long id, String name, BigDecimal price) {

    public static ServiceSummaryResponseDTO from(GarageService gs) {
        return new ServiceSummaryResponseDTO(
                gs.getOfferService().getId(),
                gs.getOfferService().getName(),
                gs.getPrice()
        );
    }
}
