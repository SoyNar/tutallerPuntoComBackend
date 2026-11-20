package com.tutallerPuntoCom.generation.Controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping( "/api/auth")
@RestController
public class AuthController {
//    @SecurityRequirements
    @GetMapping
    public ResponseEntity<String> login()
    {
        return ResponseEntity.status(HttpStatus.OK).body("Hola estas autenticado");

    }

    public ResponseEntity<String> register(){
        return ResponseEntity.status(HttpStatus.CREATED).body("usuario registrado");
    }


}
