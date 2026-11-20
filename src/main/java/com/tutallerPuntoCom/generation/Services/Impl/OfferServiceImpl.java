package com.tutallerPuntoCom.generation.Services.Impl;

import com.tutallerPuntoCom.generation.DTos.RequestDTO.ServiceRequestDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.CategoryResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.ServiceResponseDTO;
import com.tutallerPuntoCom.generation.Models.OfferService;
import com.tutallerPuntoCom.generation.Repositories.OfferServiceRepository;
import com.tutallerPuntoCom.generation.Services.Contracts.OfferServiceI;
import com.tutallerPuntoCom.generation.Utils.Enums.CategoryServices;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferServiceI {

    private final OfferServiceRepository offerServiceRepository;

    public OfferServiceImpl(OfferServiceRepository offerServiceRepository) {
        this.offerServiceRepository = offerServiceRepository;
    }

    @Override
    public List<ServiceResponseDTO> getAllServices(CategoryServices category) {
        List<OfferService> services = (category != null)
                ? offerServiceRepository.findByCategoryServices(category)
                : offerServiceRepository.findAll();

        return services.stream()
                .map(ServiceResponseDTO::from)
                .toList();
    }

    @Override
    public ServiceResponseDTO getServiceById(Long id) {
        OfferService service = offerServiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        return ServiceResponseDTO.from(service);
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        return Arrays.stream(CategoryServices.values())
                .map(CategoryResponseDTO::from)
                .toList();
    }

    @Override
    public ServiceResponseDTO createService(ServiceRequestDTO request) {
        OfferService service = new OfferService();
        service.setName(request.name().trim());
        service.setCategoryServices(request.category());
        service.setPrice(request.price());
        service.setCreatedAt(Instant.now());

        OfferService saved = offerServiceRepository.save(service);
        return ServiceResponseDTO.from(saved);
    }

    @Override
    public ServiceResponseDTO updateService(Long id, ServiceRequestDTO request) {
        OfferService service = offerServiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

        service.setName(request.name().trim());
        service.setCategoryServices(request.category());
        service.setPrice(request.price());

        OfferService updated = offerServiceRepository.save(service);
        return ServiceResponseDTO.from(updated);
    }

    @Override
    public void deleteService(Long id) {
        OfferService service = offerServiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        offerServiceRepository.delete(service);
    }
}