package com.tutallerPuntoCom.generation.Controllers;


import com.tutallerPuntoCom.generation.DTos.RequestDTO.ServiceRequestDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.ServiceResponseDTO;
import com.tutallerPuntoCom.generation.Services.Contracts.OfferServiceI;
import com.tutallerPuntoCom.generation.Utils.Enums.CategoryServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class OfferServiceController {

    private final OfferServiceI offerService;

    public OfferServiceController(OfferServiceI offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public ResponseEntity<List<ServiceResponseDTO>> getAllServices(
            @RequestParam(name = "categoria", required = false) CategoryServices categoria
    ) {
        return ResponseEntity.ok(offerService.getAllServices(categoria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponseDTO> getServiceById(@PathVariable Long id) {
        return ResponseEntity.ok(offerService.getServiceById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceResponseDTO> createService(
            @Valid @RequestBody ServiceRequestDTO request
    ) {
        ServiceResponseDTO created = offerService.createService(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceResponseDTO> updateService(
            @PathVariable Long id,
            @Valid @RequestBody ServiceRequestDTO request
    ) {
        return ResponseEntity.ok(offerService.updateService(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        offerService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}