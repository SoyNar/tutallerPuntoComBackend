package com.tutallerPuntoCom.generation.Services.Impl;

import com.tutallerPuntoCom.generation.Repositories.GarageRepository;
import com.tutallerPuntoCom.generation.Repositories.GarageServiceRepository;
import com.tutallerPuntoCom.generation.Repositories.OfferServiceRepository;
import com.tutallerPuntoCom.generation.Services.Contracts.GarageServiceSI;

public class GarageServiceSImpl implements GarageServiceSI {

    private final GarageRepository garageRepository;
    private final GarageServiceRepository garageServiceRepository;
    private final OfferServiceRepository offerServiceRepository;

    public GarageServiceSImpl(GarageRepository garageRepository, GarageServiceRepository garageServiceRepository, OfferServiceRepository offerServiceRepository) {
        this.garageRepository = garageRepository;
        this.garageServiceRepository = garageServiceRepository;
        this.offerServiceRepository = offerServiceRepository;
    }
}
