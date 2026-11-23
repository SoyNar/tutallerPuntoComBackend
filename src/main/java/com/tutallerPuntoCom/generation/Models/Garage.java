package com.tutallerPuntoCom.generation.Models;

import com.tutallerPuntoCom.generation.Utils.Enums.GarageStatus;
import com.tutallerPuntoCom.generation.Utils.Enums.TypeVehicle;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "garage")
public class Garage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nit;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false,unique = true)
    private  String email;

   @Column(nullable = false)
    private  String address;

    @Column(nullable = false)
    private  String city;

    @Column(precision = 9, scale = 6,nullable = false)
    private BigDecimal latitude;

    @Column(precision = 9,scale = 6, nullable = false)
    private BigDecimal longitude;

    private  String photoUrl;

    @Enumerated(EnumType.STRING)
    private TypeVehicle typeVehicle = TypeVehicle.AMBOS;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GarageStatus garageStatus = GarageStatus.ACTIVO;

    @CreatedDate
    private Instant createdAt;

    //citas del talleer
    @OneToMany(mappedBy = "garage")
    private List<Appointment> appointments;

    //horarios del taller
    @OneToMany(mappedBy = "garage")
    private List<GarageSchedule> garageSchedules;


}
