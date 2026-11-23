package com.tutallerPuntoCom.generation.Controllers;

import com.tutallerPuntoCom.generation.DTos.ResponseDTo.AvailabilityResponseDTO;
import com.tutallerPuntoCom.generation.Services.Impl.AvailabilityService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/workshops")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @GetMapping("/{id}/availability")
    public AvailabilityResponseDTO getAvailability(
            @PathVariable Long id,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fecha) {

        return availabilityService.getAvailability(id, fecha);
    }
}
