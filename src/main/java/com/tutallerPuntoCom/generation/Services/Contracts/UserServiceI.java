package com.tutallerPuntoCom.generation.Services.Contracts;

import com.tutallerPuntoCom.generation.DTos.ResponseDTo.PageResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.UserProfileResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.UserResponseDTO;
import org.springframework.data.domain.Pageable;

public interface UserServiceI {

    PageResponseDTO<UserResponseDTO> all(Pageable pageable);
    UserProfileResponseDTO findById(Long id);
}