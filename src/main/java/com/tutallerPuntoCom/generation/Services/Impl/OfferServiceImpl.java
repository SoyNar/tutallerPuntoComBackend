package com.tutallerPuntoCom.generation.Services.Impl;


import com.tutallerPuntoCom.generation.DTos.RequestDTO.ServiceRequestDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.CategoryResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.PageResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.ServiceResponseDTO;
import com.tutallerPuntoCom.generation.Exceptions.Customs.ConflictException;
import com.tutallerPuntoCom.generation.Exceptions.Customs.NotFoundModelException;
import com.tutallerPuntoCom.generation.Models.OfferService;
import com.tutallerPuntoCom.generation.Repositories.OfferServiceRepository;
import com.tutallerPuntoCom.generation.Services.Contracts.OfferServiceI;
import com.tutallerPuntoCom.generation.Utils.Enums.CategoryServices;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
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
    public PageResponseDTO<ServiceResponseDTO> getAllServices(CategoryServices categoria, Pageable pageable) {
        Page<OfferService> services = (categoria != null)
                ? offerServiceRepository.findByActiveTrueAndCategoryServices(categoria,pageable)
                : offerServiceRepository.findByActiveTrue(pageable);
        Page<ServiceResponseDTO> dtoPage = services.map(ServiceResponseDTO::from);
        return  PageResponseDTO.from(dtoPage);
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

        OfferService saved = offerServiceRepository.save(service);
        return ServiceResponseDTO.from(saved);
    }

    @Override
    public ServiceResponseDTO updateService(Long id, ServiceRequestDTO request) {
        OfferService service = offerServiceRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new NotFoundModelException("Servicio no encontrado"));

        service.setName(request.name().trim());
        service.setCategoryServices(request.categoryServices());
        service.setPrice(request.price());

        OfferService updated = offerServiceRepository.save(service);
        return ServiceResponseDTO.from(updated);
    }

    @Override
    public void deleteService(Long id) {
        OfferService service = offerServiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundModelException("Servicio no encontrado"));

        service.setActive(false);
        offerServiceRepository.save(service);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> getCategories() {
        return Arrays.stream(CategoryServices.values())
                .map(CategoryResponseDTO::from)
                .toList();
    }
}