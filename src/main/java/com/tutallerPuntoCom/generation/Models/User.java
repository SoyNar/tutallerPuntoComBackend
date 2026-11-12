package com.tutallerPuntoCom.generation.Models;

import com.tutallerPuntoCom.generation.Utils.Enums.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false)
    private  String name;
    @Column(nullable = false)
    private  String password;
    private  String lastname;
    @Column(unique = true,nullable = false)
    private  String email;
    private String phone;
    private  String address;
    @OneToMany(mappedBy = "user")
    private List<Vehicle> vehicles;
    @OneToMany(mappedBy = "user")
    private  List<Appointment> appointments;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Roles role;
}
