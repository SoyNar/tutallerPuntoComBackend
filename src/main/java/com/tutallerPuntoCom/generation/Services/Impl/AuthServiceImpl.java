package com.tutallerPuntoCom.generation.Services.Impl;

import com.tutallerPuntoCom.generation.DTos.RequestDTO.RegisterUserRequestDTo;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.UserResponseDTO;
import com.tutallerPuntoCom.generation.Exceptions.Customs.EmailAlreadyExistsException;
import com.tutallerPuntoCom.generation.Models.User;
import com.tutallerPuntoCom.generation.Repositories.UserRepository;
import com.tutallerPuntoCom.generation.Services.Contracts.AuthServiceI;
import com.tutallerPuntoCom.generation.Utils.Enums.Roles;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.management.relation.Role;

public class AuthServiceImpl implements AuthServiceI {

    private  final UserRepository userRepository;
    private  final PasswordEncoder passwordEncoder;


    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * MEtodo para registrar un usuario
     * usa contraseña hash para hashear la contraseña con bycript
     * @param requestDTo
     * @return  UserResponseDTO
     * */

    @Override
    public UserResponseDTO register(RegisterUserRequestDTo requestDTo) {
        boolean findUser = this.userRepository.existsByEmail(requestDTo.email());
        if(!findUser){
            throw new EmailAlreadyExistsException("Usuario ya esta registrado");
        }
        User user  = new User();
        user.setName(requestDTo.name());
        user.setLastname(requestDTo.lastname());
        user.setEmail(requestDTo.email());
        user.setPhone(requestDTo.phone());
        user.setPassword(passwordEncoder.encode(requestDTo.password()));
        user.setRole(Roles.CLIENTE);
        User savedUser = userRepository.save(user);
        return  UserResponseDTO.from(savedUser);
    }
}
