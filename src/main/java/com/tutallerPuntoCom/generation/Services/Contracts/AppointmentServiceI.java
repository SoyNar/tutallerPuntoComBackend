package com.tutallerPuntoCom.generation.Services.Contracts;

import com.tutallerPuntoCom.generation.DTos.RequestDTO.AppointmentRequestDTO;
import com.tutallerPuntoCom.generation.DTos.RequestDTO.AppointmentStatusRequestDTO;
import com.tutallerPuntoCom.generation.DTos.RequestDTO.RescheduleRequestDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.AppointmentResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.CountResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.PageResponseDTO;
import com.tutallerPuntoCom.generation.Utils.Enums.AppointmentStatus;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface AppointmentServiceI {

    AppointmentResponseDTO create(Long userId, AppointmentRequestDTO request);

    PageResponseDTO<AppointmentResponseDTO> findMyAppointments(Long userId, AppointmentStatus status, Pageable pageable);

    CountResponseDTO countMyAppointments(Long userId);

    AppointmentResponseDTO findById(Long id, Long userId);

    AppointmentResponseDTO cancel(Long id, Long userId);

    AppointmentResponseDTO reschedule(Long id, Long userId, RescheduleRequestDTO request);

    AppointmentResponseDTO updateStatus(Long id, AppointmentStatusRequestDTO request);

    PageResponseDTO<AppointmentResponseDTO> findAll(Long garageId, LocalDate date, AppointmentStatus status, Pageable pageable);
}
