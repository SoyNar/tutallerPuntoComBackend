package com.tutallerPuntoCom.generation.Models;
import com.tutallerPuntoCom.generation.Utils.Enums.DAYS;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "garage_schedule")
public class GarageSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "garage_id",nullable = false)
    private Garage garage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DAYS daysOfWeek;
   @Column(nullable = false)
    private LocalTime openAt;
    @Column(nullable = false)
    private  LocalTime closesAt;

}
