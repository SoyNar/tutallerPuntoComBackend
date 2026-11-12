package com.tutallerPuntoCom.generation.Services.Contracts;

import com.tutallerPuntoCom.generation.DTos.RequestDTO.RegisterUserRequestDTo;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.UserResponseDTO;

public interface AuthServiceI {

    UserResponseDTO register(RegisterUserRequestDTo requestDTo);


}
