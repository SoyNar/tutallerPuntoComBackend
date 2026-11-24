package com.tutallerPuntoCom.generation.Repositories;

import com.tutallerPuntoCom.generation.Models.GarageSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GarageScheduleRepository  extends JpaRepository<GarageSchedule, Long> {

    // Consulta derivada que busca por el atributo garage id
    List<GarageSchedule> findByGarageId(Long garageId);

    //
    @Modifying
    @Query("DELETE FROM GarageSchedule s WHERE s.garage.id = :garageId")
    void deleteByGarageId(@Param("garageId") Long garageId);
}
