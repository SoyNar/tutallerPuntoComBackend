package com.tutallerPuntoCom.generation.Controllers;

import com.tutallerPuntoCom.generation.DTos.RequestDTO.ScheduleDTO;
import com.tutallerPuntoCom.generation.DTos.RequestDTO.WorkshopRequestDTO;
import com.tutallerPuntoCom.generation.DTos.RequestDTO.WorkshopServicesRequestDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.PageResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.ServiceResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.WorkshopResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.WorkshopSummaryDTO;
import com.tutallerPuntoCom.generation.Services.Contracts.GarageScheduleServiceI;
import com.tutallerPuntoCom.generation.Services.Contracts.GarageServiceI;
import com.tutallerPuntoCom.generation.Services.Contracts.GarageServiceSI;
import com.tutallerPuntoCom.generation.Utils.Enums.TypeVehicle;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/workshops")
public class WorkshopController {

    private final GarageServiceI garageService;
    private final GarageScheduleServiceI scheduleService;
    private final GarageServiceSI garageServicesService;

    public WorkshopController(GarageServiceI garageService,
                              GarageScheduleServiceI scheduleService,
                              GarageServiceSI garageServicesService) {
        this.garageService = garageService;
        this.scheduleService = scheduleService;
        this.garageServicesService = garageServicesService;
    }

    @PostMapping
    public ResponseEntity<WorkshopResponseDTO> create(@Valid @RequestBody WorkshopRequestDTO request) {
        WorkshopResponseDTO response = garageService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO<WorkshopSummaryDTO>> findAll(
            @RequestParam(name = "ciudad", required = false) String city,
            @RequestParam(name = "servicio", required = false) Long serviceId,
            @RequestParam(name = "tipoVehiculo", required = false) TypeVehicle vehicleType,
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {

        return ResponseEntity.ok(garageService.findAll(city, serviceId, vehicleType, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkshopResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(garageService.findById(id));
    }

    @GetMapping("/{id}/schedule")
    public ResponseEntity<List<ScheduleDTO>> getSchedule(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.findByGarage(id));
    }

    @PutMapping("/{id}/schedule")
    public ResponseEntity<List<ScheduleDTO>> replaceSchedule(@PathVariable Long id,
                                                             @RequestBody List<ScheduleDTO> schedules) {
        return ResponseEntity.ok(scheduleService.replace(id, schedules));
    }

    @GetMapping("/{id}/services")
    public ResponseEntity<List<ServiceResponseDTO>> getServices(@PathVariable Long id) {
        return ResponseEntity.ok(garageServicesService.findByGarage(id));
    }

    @PutMapping("/{id}/services")
    public ResponseEntity<List<ServiceResponseDTO>> replaceServices(
            @PathVariable Long id,
            @Valid @RequestBody WorkshopServicesRequestDTO request) {
        return ResponseEntity.ok(garageServicesService.replace(id, request));
    }
}