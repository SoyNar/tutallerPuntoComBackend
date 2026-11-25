package com.tutallerPuntoCom.generation.DTos.ResponseDTo;

import com.tutallerPuntoCom.generation.Models.GarageSchedule;
import com.tutallerPuntoCom.generation.Utils.Enums.DAYS;

import java.time.format.DateTimeFormatter;

public record ScheduleFakeDTO(
        Long id,
        DAYS daysOfWeek,
        String openAt,
        String closesAt
) {

    private static final DateTimeFormatter HOUR_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    public static ScheduleFakeDTO from(GarageSchedule schedule) {
        return new ScheduleFakeDTO(
                schedule.getId(),
                schedule.getDaysOfWeek(),
                schedule.getOpenAt().format(HOUR_FORMAT),
                schedule.getClosesAt().format(HOUR_FORMAT)
        );
    }
}