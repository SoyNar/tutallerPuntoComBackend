package com.tutallerPuntoCom.generation.Services.Impl;


import com.tutallerPuntoCom.generation.DTos.RequestDTO.ServiceRequestDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.CategoryResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.ServiceResponseDTO;
import com.tutallerPuntoCom.generation.Exceptions.Customs.ConflictException;
import com.tutallerPuntoCom.generation.Exceptions.Customs.NotFoundModelException;
import com.tutallerPuntoCom.generation.Models.OfferService;
import com.tutallerPuntoCom.generation.Repositories.OfferServiceRepository;
import com.tutallerPuntoCom.generation.Services.Contracts.OfferServiceI;
import com.tutallerPuntoCom.generation.Utils.Enums.CategoryServices;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class OfferServiceImpl implements OfferServiceI {

    private final OfferServiceRepository offerServiceRepository;

    public OfferServiceImpl(OfferServiceRepository offerServiceRepository) {
        this.offerServiceRepository = offerServiceRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceResponseDTO> getAllServices(CategoryServices categoria) {
        List<OfferService> services = (categoria != null)
                ? offerServiceRepository.findByActiveTrueAndCategoryServices(categoria)
                : offerServiceRepository.findByActiveTrue();
        return services.stream().map(ServiceResponseDTO::from).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceResponseDTO getServiceById(Long id) {
        OfferService service = offerServiceRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new NotFoundModelException("Servicio no encontrado"));
        return ServiceResponseDTO.from(service);
    }

    @Override
    public ServiceResponseDTO createService(ServiceRequestDTO request) {
        OfferService service = new OfferService();
        service.setName(request.name().trim());
        service.setCategoryServices(request.categoryServices());
        service.setPrice(request.price());
        service.setActive(true);

        try {
            OfferService saved = offerServiceRepository.saveAndFlush(service);
            return ServiceResponseDTO.from(saved);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("No se pudo registrar el servicio por conflicto de datos");
        }
    }

    @Override
    public ServiceResponseDTO updateService(Long id, ServiceRequestDTO request) {
        OfferService service = offerServiceRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new NotFoundModelException("Servicio no encontrado"));

        service.setName(request.name().trim());
        service.setCategoryServices(request.categoryServices());
        service.setPrice(request.price());

        try {
            OfferService updated = offerServiceRepository.saveAndFlush(service);
            return ServiceResponseDTO.from(updated);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("No se pudo actualizar el servicio por conflicto de datos");
        }
    }

    @Override
    public void deleteService(Long id) {
        OfferService service = offerServiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundModelException("Servicio no encontrado"));

        service.setActive(false);
        try {
            offerServiceRepository.saveAndFlush(service);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("No se puede eliminar el servicio porque está en uso");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> getCategories() {
        return Arrays.stream(CategoryServices.values())
                .map(CategoryResponseDTO::from)
                .toList();
    }
}