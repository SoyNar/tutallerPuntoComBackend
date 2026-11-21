package com.tutallerPuntoCom.generation.DTos.ResponseDTo;

import com.tutallerPuntoCom.generation.Models.User;

public record UserResponseDTO (Long id,
                               String lastname,
                               String name,
                               String email,
                               String role
){

    public static  UserResponseDTO from(User user){
        return  new UserResponseDTO(
                user.getId(),
                user.getLastname(),
                user.getName(),
                user.getEmail(),
                user.getRole().name()
        );

    }
}


