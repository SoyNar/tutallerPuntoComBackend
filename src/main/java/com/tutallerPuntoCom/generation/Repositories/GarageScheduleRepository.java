package com.tutallerPuntoCom.generation.Repositories;

import com.tutallerPuntoCom.generation.Models.GarageSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GarageScheduleRepository
        extends JpaRepository<GarageSchedule, Long> {

    List<GarageSchedule> findByGarageId(Long garageId);
}
