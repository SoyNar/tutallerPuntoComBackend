package com.tutallerPuntoCom.generation.Services.Impl;

import com.tutallerPuntoCom.generation.DTos.RequestDTO.ScheduleDTO;
import com.tutallerPuntoCom.generation.Exceptions.Customs.BusinessRuleException;
import com.tutallerPuntoCom.generation.Exceptions.Customs.NotFoundModelException;
import com.tutallerPuntoCom.generation.Models.Garage;
import com.tutallerPuntoCom.generation.Models.GarageSchedule;
import com.tutallerPuntoCom.generation.Repositories.GarageRepository;
import com.tutallerPuntoCom.generation.Repositories.GarageScheduleRepository;
import com.tutallerPuntoCom.generation.Services.Contracts.GarageScheduleServiceI;
import com.tutallerPuntoCom.generation.Utils.Enums.DAYS;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GarageScheduleServiceImpl  implements GarageScheduleServiceI {

    private final GarageRepository garageRepository;
    private final GarageScheduleRepository scheduleRepository;

    public GarageScheduleServiceImpl(GarageRepository garageRepository, GarageScheduleRepository scheduleRepository) {
        this.garageRepository = garageRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleDTO> findByGarage(Long garageId){
        return null;
    }


    // Metodos de apoyo

    //Encontrar taller por su id
    private Garage findGarageOrThrow(Long garageId){
        return garageRepository.findById(garageId)
                .orElseThrow(() -> new NotFoundModelException("No existe un taller con id " + garageId));
    }

    //
    private void validate(List<ScheduleDTO> schedule){

        // valida si los horarios estan vacios
        if (schedule == null){
            throw new BusinessRuleException("Se debe enviar la lista de horarios");
        }
        // Control de dia duplicados por medio de un Set
        Set<DAYS> seenDays = new HashSet<>();
        for (ScheduleDTO s : schedules){
            // se aseguria que no haya campos vacios
            if (s == null || s.day() == null || s.openAt() == null | s.closeAt() == null){
                throw new BusinessRuleException("Cada horario debe incluir día, hora de apertura y hora de cierre");
            }
            // Validacion de horarios
            if (!s.openAt().isBefore(s.closeAt())){
                throw new BusinessRuleException("La hora de apertura debe ser menor que la hora de cierre");
            }
            // Si ya un dia repetido lanza la excepcion
            if (!seenDays.add(s.day())) {
                throw new BusinessRuleException("El día " + s.day() + " está repetido");
            }
        }

    }

    // Ordena los horarios por dia de la semana
    private List<ScheduleDTO> toSortedDtos(List<GarageSchedule> schedules) {
        return schedules.stream()
                .sorted(Comparator.comparing(GarageSchedule::getDaysOfWeek))
                .map(ScheduleDTO::from)
                .toList();
    }
}
