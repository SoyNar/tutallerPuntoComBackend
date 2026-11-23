package com.tutallerPuntoCom.generation.Services.Impl;

import com.tutallerPuntoCom.generation.DTos.ResponseDTo.ServiceResponseDTO;
import com.tutallerPuntoCom.generation.Exceptions.Customs.NotFoundModelException;
import com.tutallerPuntoCom.generation.Models.Garage;
import com.tutallerPuntoCom.generation.Models.GarageService;
import com.tutallerPuntoCom.generation.Models.OfferService;
import com.tutallerPuntoCom.generation.Repositories.GarageRepository;
import com.tutallerPuntoCom.generation.Repositories.GarageServiceRepository;
import com.tutallerPuntoCom.generation.Repositories.OfferServiceRepository;
import com.tutallerPuntoCom.generation.Services.Contracts.GarageServiceSI;

import java.math.BigDecimal;

public class GarageServiceSImpl implements GarageServiceSI {

    private final GarageRepository garageRepository;
    private final GarageServiceRepository garageServiceRepository;
    private final OfferServiceRepository offerServiceRepository;

    public GarageServiceSImpl(GarageRepository garageRepository, GarageServiceRepository garageServiceRepository, OfferServiceRepository offerServiceRepository) {
        this.garageRepository = garageRepository;
        this.garageServiceRepository = garageServiceRepository;
        this.offerServiceRepository = offerServiceRepository;
    }

    // Metodos

    // Metodos privados

    //Busca un taller por su id o lanza un mensaje de error
    private Garage findGarage(Long garageId){
        return garageRepository.findById(garageId)
                .orElseThrow(() -> new NotFoundModelException("No existe un taller con id " + garageId));
    }

    // Convierte GarageService a DTO y
    // usa el precio del taller si existe, o el del catálogo si viene nulo
    private ServiceResponseDTO toDto(GarageService garageService) {
        OfferService offer = garageService.getOfferService();
        BigDecimal price = garageService.getPrice() != null
                ? garageService.getPrice()
                : offer.getPrice();
        return new ServiceResponseDTO(
                offer.getId(),
                offer.getName(),
                offer.getCategoryServices(),
                price,
                offer.getActive(),
                offer.getCreatedAt());
    }
}
