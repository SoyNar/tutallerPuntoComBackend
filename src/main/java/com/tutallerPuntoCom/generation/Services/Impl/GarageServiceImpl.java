package com.tutallerPuntoCom.generation.Services.Impl;

import com.tutallerPuntoCom.generation.DTos.RequestDTO.WorkshopRequestDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.PageResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.WorkshopResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.WorkshopSummaryDTO;
import com.tutallerPuntoCom.generation.Exceptions.Customs.EmailAlreadyExistsException;
import com.tutallerPuntoCom.generation.Exceptions.Customs.NotFoundModelException;
import com.tutallerPuntoCom.generation.Models.Garage;
import com.tutallerPuntoCom.generation.Repositories.GarageRepository;
import com.tutallerPuntoCom.generation.Services.Contracts.GarageScheduleServiceI;
import com.tutallerPuntoCom.generation.Services.Contracts.GarageServiceI;
import com.tutallerPuntoCom.generation.Services.Contracts.GarageServiceSI;
import com.tutallerPuntoCom.generation.Utils.Enums.GarageStatus;
import com.tutallerPuntoCom.generation.Utils.Enums.TypeVehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GarageServiceImpl implements GarageServiceI {

    private final GarageRepository garageRepository;
    private final GarageScheduleServiceI scheduleService;
    private final GarageServiceSI garageServicesService;

    // Constructor
    public GarageServiceImpl(GarageRepository garageRepository, GarageScheduleServiceI scheduleService,
                             GarageServiceSI garageServicesService) {
        this.garageRepository = garageRepository;
        this.scheduleService = scheduleService;
        this.garageServicesService = garageServicesService;
    }

    // Crea un nuevo taller en la BD
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


    // Filtra para buscar todos los talleres activos y devuelve informacion
    // necesaria de los talleres
    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<WorkshopSummaryDTO> findAll(
            String city,
            Long serviceId,
            TypeVehicle vehicleType,
            Pageable pageable) {

        // Convierte dato a null para que el filtro lo ignore y limpia espacios en blanco
        String cleanCity = (city == null || city.isBlank()) ? null : city.trim();

        Page<Garage> page = garageRepository.search(
                GarageStatus.ACTIVO, cleanCity, vehicleType, serviceId, pageable);

        return PageResponseDTO.from(page.map(WorkshopSummaryDTO::from));
    }

    // Busca y valida si existe un taller segun su id
    @Override
    @Transactional(readOnly = true)
    public WorkshopResponseDTO findById(Long id) {
        Garage garage = garageRepository.findById(id)
                .orElseThrow(() -> new NotFoundModelException("No existe un taller con id " + id));
        return WorkshopResponseDTO.from(garage);
    }
}
