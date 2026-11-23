package com.tutallerPuntoCom.generation.Services.Impl;

import com.tutallerPuntoCom.generation.DTos.RequestDTO.ContactRequestDTO;
import com.tutallerPuntoCom.generation.Models.Garage;
import com.tutallerPuntoCom.generation.Models.OfferService;
import com.tutallerPuntoCom.generation.Repositories.GarageRepository;
import com.tutallerPuntoCom.generation.Repositories.OfferServiceRepository;
import com.tutallerPuntoCom.generation.Services.Contracts.EmailServiceI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class Module5Service {

    private final GarageRepository garageRepository;
    private final OfferServiceRepository offerServiceRepository;
    private final EmailServiceI emailService;

    @Value("${app.contact.email:}")
    private String contactEmail;

    private final Map<String, List<LocalDateTime>> solicitudes =
            new ConcurrentHashMap<>();

    private static final int MAX_SOLICITUDES = 3;
    private static final int MINUTOS = 10;

    public Module5Service(
            GarageRepository garageRepository,
            OfferServiceRepository offerServiceRepository,
            EmailServiceI emailService) {

        this.garageRepository = garageRepository;
        this.offerServiceRepository = offerServiceRepository;
        this.emailService = emailService;
    }

    public void sendContactRequest(ContactRequestDTO request, String ip) {

        validarAntiSpam(request.email(), ip);

        String destinatario = contactEmail;
        String nombreTaller = "TuTaller";

        if (request.garageId() != null) {

            Garage garage = garageRepository.findById(request.garageId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "El taller no existe"));

            destinatario = garage.getEmail();
            nombreTaller = garage.getName();
        }

        if (destinatario == null || destinatario.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "El correo de destino no está configurado");
        }

        List<String> servicios = request.serviceIds() == null
                ? List.of()
                : offerServiceRepository.findAllById(request.serviceIds())
                .stream()
                .map(OfferService::getName)
                .toList();

        Map<String, Object> variables = new HashMap<>();

        variables.put("nombreCliente", request.name());
        variables.put("correoCliente", request.email());
        variables.put("telefonoCliente", request.telefono());
        variables.put("mensaje", request.mensaje());
        variables.put("nombreTaller", nombreTaller);
        variables.put("listaServicios", String.join(", ", servicios));

        emailService.sendTemplate(
                destinatario,
                "Solicitud de cotización",
                "SOLICITUD_COTIZACION",
                variables
        );
    }

    private synchronized void validarAntiSpam(String email, String ip) {

        LocalDateTime ahora = LocalDateTime.now();

        String claveEmail = "EMAIL:" + email.toLowerCase().trim();
        String claveIp = "IP:" + ip;

        List<LocalDateTime> fechasEmail = obtenerSolicitudes(claveEmail, ahora);
        List<LocalDateTime> fechasIp = obtenerSolicitudes(claveIp, ahora);

        if (fechasEmail.size() >= MAX_SOLICITUDES
                || fechasIp.size() >= MAX_SOLICITUDES) {

            throw new ResponseStatusException(
                    HttpStatus.TOO_MANY_REQUESTS,
                    "Has superado el límite de solicitudes. Intenta nuevamente en 10 minutos."
            );
        }

        fechasEmail.add(ahora);
        fechasIp.add(ahora);
    }

    private List<LocalDateTime> obtenerSolicitudes(
            String clave, LocalDateTime ahora) {

        List<LocalDateTime> fechas = solicitudes.computeIfAbsent(
                clave, k -> new ArrayList<>());

        fechas.removeIf(fecha ->
                fecha.isBefore(ahora.minusMinutes(MINUTOS)));

        return fechas;
    }
}
