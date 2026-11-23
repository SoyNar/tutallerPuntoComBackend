
package com.tutallerPuntoCom.generation.Controllers;

import com.tutallerPuntoCom.generation.DTos.RequestDTO.ContactRequestDTO;
import com.tutallerPuntoCom.generation.Services.Impl.Module5Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact-requests")
public class ContactController {

    private final Module5Service module5Service;

    public ContactController(Module5Service module5Service) {
        this.module5Service = module5Service;
    }

    @PostMapping
    public ResponseEntity<String> sendContactRequest(
            @Valid @RequestBody ContactRequestDTO request,
            HttpServletRequest httpRequest) {

        String ip = httpRequest.getRemoteAddr();

        module5Service.sendContactRequest(request, ip);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("Solicitud de cotización recibida correctamente");
    }
}
