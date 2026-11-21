package com.tutallerPuntoCom.generation.Services.Impl;

import com.tutallerPuntoCom.generation.DTos.RequestDTO.ScheduleDTO;
import com.tutallerPuntoCom.generation.Exceptions.Customs.NotFoundModelException;
import com.tutallerPuntoCom.generation.Models.Garage;
import com.tutallerPuntoCom.generation.Repositories.GarageRepository;
import com.tutallerPuntoCom.generation.Repositories.GarageScheduleRepository;
import com.tutallerPuntoCom.generation.Services.Contracts.GarageScheduleServiceI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

}
