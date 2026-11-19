package com.tutallerPuntoCom.generation.Services.Contracts;

import com.tutallerPuntoCom.generation.DTos.RequestDTO.RegisterUserRequestDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.UserResponseDTO;

public interface AuthServiceI {

    UserResponseDTO register(RegisterUserRequestDTO requestDTo);


}
