package com.tutallerPuntoCom.generation.Config;

import com.tutallerPuntoCom.generation.Models.User;
import com.tutallerPuntoCom.generation.Models.Garage;
import com.tutallerPuntoCom.generation.Models.GarageSchedule;
import com.tutallerPuntoCom.generation.Models.OfferService;

import com.tutallerPuntoCom.generation.Repositories.UserRepository;
import com.tutallerPuntoCom.generation.Repositories.GarageRepository;
import com.tutallerPuntoCom.generation.Repositories.GarageScheduleRepository;
import com.tutallerPuntoCom.generation.Repositories.OfferServiceRepository;

import com.tutallerPuntoCom.generation.Utils.Enums.Roles;
import com.tutallerPuntoCom.generation.Utils.Enums.GarageStatus;
import com.tutallerPuntoCom.generation.Utils.Enums.TypeVehicle;
import com.tutallerPuntoCom.generation.Utils.Enums.CategoryServices;
import com.tutallerPuntoCom.generation.Utils.Enums.DAYS;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final GarageRepository garageRepository;
    private final GarageScheduleRepository scheduleRepository;
    private final OfferServiceRepository serviceRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${ADMIN_EMAIL:}")
    private String adminEmail;

    @Value("${ADMIN_PASSWORD:}")
    private String adminPassword;

    public DataSeeder(
            UserRepository userRepository,
            GarageRepository garageRepository,
            GarageScheduleRepository scheduleRepository,
            OfferServiceRepository serviceRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.garageRepository = garageRepository;
        this.scheduleRepository = scheduleRepository;
        this.serviceRepository = serviceRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        crearAdministrador();
        crearServicios();
        crearTalleres();
        crearHorarios();

    }

    private void crearAdministrador() {

        if (adminEmail == null || adminEmail.isBlank()
                || adminPassword == null || adminPassword.isBlank()) {

            System.out.println("Administrador no creado: faltan ADMIN_EMAIL y ADMIN_PASSWORD.");
            return;
        }

        String correo = adminEmail.trim().toLowerCase();

        if (!userRepository.existsByEmail(correo)) {

            User admin = new User();

            admin.setName("Administrador");
            admin.setLastname("TuTaller");
            admin.setEmail(correo);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRole(Roles.ADMIN);

            userRepository.save(admin);

            System.out.println("Administrador creado correctamente.");
        }
    }

    private void crearServicios() {

        if (serviceRepository.count() > 0) {
            return;
        }

        List<OfferService> servicios = new ArrayList<>();

        servicios.add(crearServicio(
                "Mecánica general",
                CategoryServices.MECANICA_GENERAL,
                "80000"
        ));

        servicios.add(crearServicio(
                "Latonería y pintura",
                CategoryServices.LATONERIA_PINTURA,
                "150000"
        ));

        servicios.add(crearServicio(
                "Diagnóstico electrónico",
                CategoryServices.ELECTRONICA,
                "60000"
        ));

        servicios.add(crearServicio(
                "Equipamiento del vehículo",
                CategoryServices.EQUIPAMENTO_VEHICULO,
                "50000"
        ));

        serviceRepository.saveAll(servicios);

        System.out.println("Catálogo de servicios creado.");
    }

    private OfferService crearServicio(
            String nombre,
            CategoryServices categoria,
            String precio) {

        OfferService servicio = new OfferService();

        servicio.setName(nombre);
        servicio.setCategoryServices(categoria);
        servicio.setPrice(new BigDecimal(precio));

        return servicio;
    }

    private void crearTalleres() {

        if (garageRepository.count() > 0) {
            return;
        }

        Garage taller1 = new Garage();

        taller1.setName("Taller de prueba 1");
        taller1.setNit("900000001-1");
        taller1.setPhone("3000000001");
        taller1.setEmail("taller1@ejemplo.test");
        taller1.setAddress("Calle 10 # 20-30");
        taller1.setCity("Bogotá");
        taller1.setLatitude(new BigDecimal("4.6097"));
        taller1.setLongitude(new BigDecimal("-74.0817"));
        taller1.setTypeVehicle(TypeVehicle.AMBOS);
        taller1.setGarageStatus(GarageStatus.ACTIVO);

        Garage taller2 = new Garage();

        taller2.setName("Taller de prueba 2");
        taller2.setNit("900000002-2");
        taller2.setPhone("3000000002");
        taller2.setEmail("taller2@ejemplo.test");
        taller2.setAddress("Carrera 15 # 80-20");
        taller2.setCity("Bogotá");
        taller2.setLatitude(new BigDecimal("4.6675"));
        taller2.setLongitude(new BigDecimal("-74.0550"));
        taller2.setTypeVehicle(TypeVehicle.CARRO);
        taller2.setGarageStatus(GarageStatus.ACTIVO);

        garageRepository.saveAll(List.of(taller1, taller2));

        System.out.println("Talleres de prueba creados.");
    }

    private void crearHorarios() {

        if (scheduleRepository.count() > 0) {
            return;
        }

        List<Garage> talleres = garageRepository.findAll();
        List<GarageSchedule> horarios = new ArrayList<>();

        for (Garage taller : talleres) {

            for (DAYS dia : DAYS.values()) {

                if (dia == DAYS.DOMINGO) {
                    continue;
                }

                GarageSchedule horario = new GarageSchedule();

                horario.setGarage(taller);
                horario.setDaysOfWeek(dia);
                horario.setOpenAt(LocalTime.of(8, 0));
                horario.setClosesAt(LocalTime.of(17, 0));

                horarios.add(horario);
            }
        }

        scheduleRepository.saveAll(horarios);

        System.out.println("Horarios de talleres creados.");
    }
}
