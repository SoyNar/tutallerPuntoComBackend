package com.tutallerPuntoCom.generation.Services.Impl;

import com.tutallerPuntoCom.generation.DTos.RequestDTO.WorkshopRequestDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.WorkshopResponseDTO;
import com.tutallerPuntoCom.generation.Exceptions.Customs.EmailAlreadyExistsException;
import com.tutallerPuntoCom.generation.Models.Garage;
import com.tutallerPuntoCom.generation.Repositories.GarageRepository;
import com.tutallerPuntoCom.generation.Services.Contracts.GarageServiceI;
import org.springframework.stereotype.Service;

@Service
public class GarageServiceImpl implements GarageServiceI {

    private final GarageRepository garageRepository;

    // Constructor
    public GarageServiceImpl(GarageRepository garageRepository) {
        this.garageRepository = garageRepository;
    }

    @Override
    public WorkshopResponseDTO create(WorkshopRequestDTO request){
        if (garageRepository.existsByEmail(request.email())){
            throw new EmailAlreadyExistsException("Ya existe un taller registrado con ese correo");
        }

        Garage garage = new Garage();
        garage.setName(request.name());
        garage.setNit(request.nit());
        garage.setPhone(request.phone());
        garage.setEmail(request.email());
        garage.setAddress(request.address());
        garage.setCity(request.city());
        garage.setLatitude(request.latitude());
        garage.setLongitude(request.longitude());
        garage.setPhotoUrl(request.photoUrl());
        garage.setTypeVehicle(request.typeVehicle());

        Garage saved = garageRepository.save(garage);
        return WorkshopResponseDTO.from(saved);
    }
}
