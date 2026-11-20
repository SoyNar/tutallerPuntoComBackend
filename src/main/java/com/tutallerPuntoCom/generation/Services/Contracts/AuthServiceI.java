package com.tutallerPuntoCom.generation.Services.Contracts;

import com.tutallerPuntoCom.generation.DTos.RequestDTO.LoginRequestDTO;
import com.tutallerPuntoCom.generation.DTos.RequestDTO.RegisterUserRequestDTo;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.AuhResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.UserResponseDTO;

public interface AuthServiceI {

    AuhResponseDTO register(RegisterUserRequestDTo requestDTo);
    AuhResponseDTO login(LoginRequestDTO requestDTO);


}
