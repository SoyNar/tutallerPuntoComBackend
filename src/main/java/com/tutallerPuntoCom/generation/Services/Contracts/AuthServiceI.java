package com.tutallerPuntoCom.generation.Services.Contracts;

import com.tutallerPuntoCom.generation.DTos.RequestDTO.LoginRequestDTO;
import com.tutallerPuntoCom.generation.DTos.RequestDTO.RegisterUserRequestDTo;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.AuthResponseDTO;

public interface AuthServiceI {

    AuthResponseDTO register(RegisterUserRequestDTo requestDTo);
    AuthResponseDTO login(LoginRequestDTO requestDTO);


}
