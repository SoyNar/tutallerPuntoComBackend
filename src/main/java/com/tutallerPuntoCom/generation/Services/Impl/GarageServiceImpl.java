package com.tutallerPuntoCom.generation.Services.Impl;

import com.tutallerPuntoCom.generation.DTos.RequestDTO.ScheduleDTO;
import com.tutallerPuntoCom.generation.DTos.RequestDTO.WorkshopRequestDTO;
import com.tutallerPuntoCom.generation.DTos.RequestDTO.WorkshopServicesRequestDTO;
import com.tutallerPuntoCom.generation.DTos.RequestDTO.WorkshopStatusRequestDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.PageResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.ServiceResponseDTO;
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

import java.util.List;

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
    @Transactional
    public WorkshopResponseDTO create(WorkshopRequestDTO request){
        if (garageRepository.existsByEmail(request.email())){
            throw new EmailAlreadyExistsException("Ya existe un taller registrado con ese correo");
        }

        Garage garage = new Garage();

        applyData(garage, request);

        Garage saved = garageRepository.save(garage);

        List<ScheduleDTO> schedule = scheduleService.replace(saved.getId(), request.schedule());
        List<ServiceResponseDTO> services = garageServicesService.replace(
                saved.getId(), new WorkshopServicesRequestDTO(request.services()));

        return WorkshopResponseDTO.from(saved, schedule, services);
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

        return WorkshopResponseDTO.from(
                garage,
                scheduleService.findByGarage(id),
                garageServicesService.findByGarage(id));
    }

    @Override
    public WorkshopResponseDTO update(Long id, WorkshopRequestDTO request) {
        return null;
    }

    @Override
    public WorkshopResponseDTO changeStatus(Long id, WorkshopStatusRequestDTO request) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    // Metodos privados
    private Garage findGarage(Long id){
        return garageRepository.findById(id)
                .orElseThrow(() -> new NotFoundModelException("No existe un taller con id " + id));
    }

    // Copiar datos del request a la entidad
    private void applyData(Garage garage, WorkshopRequestDTO request){
        garage.setName(request.name());
        garage.setNit(request.nit());
        garage.setPhone(request.phone());
        garage.setEmail(request.email());
        garage.setAddress(request.address());
        garage.setCity(request.city());
        garage.setLatitude(request.latitude());
        garage.setLongitude(request.longitude());
        garage.setTypeVehicle(request.typeVehicle());
        garage.setGarageStatus(request.garageStatus());

        // Remplaza la foto si la trae el request, si no la deja como esta
        if (request.photoUrl() != null) {
            garage.setPhotoUrl(request.photoUrl());
        }
    }

    // Arma la respuesta completa
    private WorkshopResponseDTO toResponse(Garage garage){
        return WorkshopResponseDTO.from(
                garage,
                scheduleService.findByGarage(garage.getId()),
                garageServicesService.findByGarage(garage.getId()));
    }
}
