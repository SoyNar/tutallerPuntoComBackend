package com.tutallerPuntoCom.generation.Services.Impl;

import com.tutallerPuntoCom.generation.DTos.RequestDTO.WorkshopServicesRequestDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.ServiceResponseDTO;
import com.tutallerPuntoCom.generation.Exceptions.Customs.BusinessRuleException;
import com.tutallerPuntoCom.generation.Exceptions.Customs.NotFoundModelException;
import com.tutallerPuntoCom.generation.Models.Garage;
import com.tutallerPuntoCom.generation.Models.GarageService;
import com.tutallerPuntoCom.generation.Models.OfferService;
import com.tutallerPuntoCom.generation.Repositories.GarageRepository;
import com.tutallerPuntoCom.generation.Repositories.GarageServiceRepository;
import com.tutallerPuntoCom.generation.Repositories.OfferServiceRepository;
import com.tutallerPuntoCom.generation.Services.Contracts.GarageServiceSI;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
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

    //Consulta y devuelve todos los servicios de un taller
    @Override
    @Transactional(readOnly = true)
    public List<ServiceResponseDTO> findByGarage(Long garageId) {
        findGarage(garageId);
        return garageServiceRepository.findByGarageIdWithService(garageId).stream()
                .map(this::toDto)
                .toList();
    }

    // Logica para remplazar lista de servicios de un taller
    @Override
    @Transactional
    public List<ServiceResponseDTO> replace(Long garageId, WorkshopServicesRequestDTO request) {
        Garage garage = findGarage(garageId);
        List<WorkshopServicesRequestDTO.Item> items = request.services();

        // Sin servicios repetidos en la petición
        Set<Long> ids = new HashSet<>();
        for (WorkshopServicesRequestDTO.Item item : items) {
            if (!ids.add(item.serviceId())) {
                throw new BusinessRuleException("El servicio con id " + item.serviceId() + " está repetido");
            }
        }

        // Todos los servicios deben existir en el catálogo
        Map<Long, OfferService> found = offerServiceRepository.findAllById(ids).stream()
                .collect(Collectors.toMap(OfferService::getId, Function.identity()));

        List<Long> missing = ids.stream().filter(id -> !found.containsKey(id)).sorted().toList();
        if (!missing.isEmpty()) {
            throw new BusinessRuleException("No existen servicios en el catálogo con id: " + missing);
        }

        // Solo se pueden asignar servicios activos
        List<Long> inactive = found.values().stream()
                .filter(service -> !Boolean.TRUE.equals(service.getActive()))
                .map(OfferService::getId)
                .sorted()
                .toList();
        if (!inactive.isEmpty()) {
            throw new BusinessRuleException("Los servicios con id " + inactive + " están inactivos");
        }

        // Elimina y guarda los datos en una sola transaccion
        garageServiceRepository.deleteByGarageId(garageId);

        List<GarageService> entities = items.stream()
                .map(item -> {
                    GarageService garageService = new GarageService();
                    garageService.setGarage(garage);
                    garageService.setOfferService(found.get(item.serviceId()));
                    garageService.setPrice(item.price());
                    return garageService;
                })
                .toList();

        return garageServiceRepository.saveAll(entities).stream()
                .map(this::toDto)
                .sorted(Comparator.comparing(ServiceResponseDTO::name))
                .toList();
    }

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
