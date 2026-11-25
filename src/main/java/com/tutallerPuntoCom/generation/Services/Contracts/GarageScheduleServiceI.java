package com.tutallerPuntoCom.generation.Services.Contracts;

import com.tutallerPuntoCom.generation.DTos.RequestDTO.ScheduleDTO;

import java.util.List;

public interface GarageScheduleServiceI {

    // Lista de horarios de un taller
    List<ScheduleDTO> findByGarage(Long garageId);


    // Editar horarios de un taller
    List<ScheduleDTO> replace(Long garageId, List<ScheduleDTO> schedules);
}