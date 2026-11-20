package com.tutallerPuntoCom.generation.Controllers;

import com.tutallerPuntoCom.generation.DTos.RequestDTO.LoginRequestDTO;
import com.tutallerPuntoCom.generation.DTos.RequestDTO.RegisterUserRequestDTo;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.AuhResponseDTO;
import com.tutallerPuntoCom.generation.Services.Contracts.AuthServiceI;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping( "/api/auth")
@RestController
public class AuthController {

    private final AuthServiceI authServiceI;

    public  AuthController(AuthServiceI authServiceI){
        this.authServiceI = authServiceI;
    }

    /**
     *
     * */
    @PostMapping("/register")
    public ResponseEntity<AuhResponseDTO> register(  @Valid  @RequestBody RegisterUserRequestDTo requestDTo){
        AuhResponseDTO responseDTO =  this.authServiceI.register(requestDTo);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    /**
     * Metodo para loguear un usuario devuelve el usuairo y token
     *
     * */

    @PostMapping("/login")
    public ResponseEntity<AuhResponseDTO> login (@Valid @RequestBody LoginRequestDTO requestDTO){
        AuhResponseDTO responseDTO = this.authServiceI.login(requestDTO);
        return  ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }


}
