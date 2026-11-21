package com.tutallerPuntoCom.generation.DTos.RequestDTO;

import com.tutallerPuntoCom.generation.Models.GarageSchedule;
import com.tutallerPuntoCom.generation.Utils.Enums.DAYS;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record ScheduleDTO(
        @NotNull(message = "El dia es obligatorio")
        DAYS day,

        @NotNull(message = "La hora de apertura es obligatoria")
        LocalTime openAt,

        @NotNull(message = "La hora de cierre es obligatoria")
        LocalTime closeAt
) {
    public static ScheduleDTO from(GarageSchedule schedule){
        return new ScheduleDTO(
                schedule.getDaysOfWeek(),
                schedule.getOpenAt(),
                schedule.getClosesAt()
        );
    }
}
