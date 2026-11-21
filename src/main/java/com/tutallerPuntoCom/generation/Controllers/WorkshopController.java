package com.tutallerPuntoCom.generation.Controllers;

import com.tutallerPuntoCom.generation.DTos.RequestDTO.WorkshopRequestDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.PageResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.WorkshopResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.WorkshopSummaryDTO;
import com.tutallerPuntoCom.generation.Services.Contracts.GarageServiceI;
import com.tutallerPuntoCom.generation.Utils.Enums.TypeVehicle;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/workshops")
public class WorkshopController {

    private final GarageServiceI garageService;

    public WorkshopController(GarageServiceI garageService) {
        this.garageService = garageService;
    }

    @PostMapping
    public ResponseEntity<WorkshopResponseDTO> create(@Valid @RequestBody WorkshopRequestDTO request){

        WorkshopResponseDTO response = garageService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO<WorkshopSummaryDTO>> findAll(
            @RequestParam(name = "ciudad", required = false) String city,
            @RequestParam(name = "servicio", required = false) Long serviceId,
            @RequestParam(name = "tipoVehiculo", required = false) TypeVehicle vehicleType,
            @PageableDefault(size = 10, sort = "name")Pageable pageable) {

        return  ResponseEntity.ok(garageService.findAll(city, serviceId, vehicleType, pageable));
    }



}
