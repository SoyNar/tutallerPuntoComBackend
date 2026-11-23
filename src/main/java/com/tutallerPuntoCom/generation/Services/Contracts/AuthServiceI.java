package com.tutallerPuntoCom.generation.Services.Contracts;

import com.tutallerPuntoCom.generation.DTos.RequestDTO.LoginRequestDTO;
import com.tutallerPuntoCom.generation.DTos.RequestDTO.RegisterUserRequestDTo;
import com.tutallerPuntoCom.generation.DTos.RequestDTO.VehicleRequestDto;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.AuthResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.RegisterResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.VehicleResponseDTO;

public interface AuthServiceI {

    RegisterResponseDTO register(RegisterUserRequestDTo requestDTo);
    AuthResponseDTO login(LoginRequestDTO requestDTO);


}
