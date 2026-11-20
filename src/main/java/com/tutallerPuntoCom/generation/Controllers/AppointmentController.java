package com.tutallerPuntoCom.generation.Controllers;

import com.tutallerPuntoCom.generation.DTos.RequestDTO.AppointmentRequestDTO;
import com.tutallerPuntoCom.generation.DTos.RequestDTO.AppointmentStatusRequestDTO;
import com.tutallerPuntoCom.generation.DTos.RequestDTO.RescheduleRequestDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.AppointmentResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.CountResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.PageResponseDTO;
import com.tutallerPuntoCom.generation.Security.AuthUser;
import com.tutallerPuntoCom.generation.Services.Contracts.AppointmentServiceI;
import com.tutallerPuntoCom.generation.Utils.Enums.AppointmentStatus;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentServiceI appointmentService;

    public AppointmentController(AppointmentServiceI appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<AppointmentResponseDTO> create(@AuthenticationPrincipal AuthUser user,
                                                         @Valid @RequestBody AppointmentRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.create(user.id(), request));
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PageResponseDTO<AppointmentResponseDTO>> findMyAppointments(@AuthenticationPrincipal AuthUser user,
                                                                                      @RequestParam(name = "estado", required = false) AppointmentStatus status,
                                                                                      Pageable pageable) {
        return ResponseEntity.ok(appointmentService.findMyAppointments(user.id(), status, pageable));
    }

    @GetMapping("/me/count")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CountResponseDTO> countMyAppointments(@AuthenticationPrincipal AuthUser user) {
        return ResponseEntity.ok(appointmentService.countMyAppointments(user.id()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AppointmentResponseDTO> findById(@PathVariable Long id,
                                                           @AuthenticationPrincipal AuthUser user) {
        return ResponseEntity.ok(appointmentService.findById(id, user.id()));
    }

    @PatchMapping("/{id}/cancel")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AppointmentResponseDTO> cancel(@PathVariable Long id,
                                                         @AuthenticationPrincipal AuthUser user) {
        return ResponseEntity.ok(appointmentService.cancel(id, user.id()));
    }

    @PatchMapping("/{id}/reschedule")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AppointmentResponseDTO> reschedule(@PathVariable Long id,
                                                             @AuthenticationPrincipal AuthUser user,
                                                             @Valid @RequestBody RescheduleRequestDTO request) {
        return ResponseEntity.ok(appointmentService.reschedule(id, user.id(), request));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AppointmentResponseDTO> updateStatus(@PathVariable Long id,
                                                               @Valid @RequestBody AppointmentStatusRequestDTO request) {
        return ResponseEntity.ok(appointmentService.updateStatus(id, request));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageResponseDTO<AppointmentResponseDTO>> findAll(@RequestParam(name = "taller", required = false) Long garageId,
                                                                           @RequestParam(name = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                                           @RequestParam(name = "estado", required = false) AppointmentStatus status,
                                                                           Pageable pageable) {
        return ResponseEntity.ok(appointmentService.findAll(garageId, date, status, pageable));
    }
}
