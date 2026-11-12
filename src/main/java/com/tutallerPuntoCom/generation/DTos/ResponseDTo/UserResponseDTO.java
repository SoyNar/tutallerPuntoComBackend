package com.tutallerPuntoCom.generation.DTos.ResponseDTo;

import com.tutallerPuntoCom.generation.Models.User;

public record UserResponseDTO (Long id, String name, String email){

    public static  UserResponseDTO from(User user){
        return  new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        );

    }
}


