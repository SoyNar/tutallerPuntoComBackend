package com.tutallerPuntoCom.generation.Services.Impl;

import com.tutallerPuntoCom.generation.DTos.RequestDTO.LoginRequestDTO;
import com.tutallerPuntoCom.generation.DTos.RequestDTO.VehicleRequestDto;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.RegisterResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.VehicleResponseDTO;
import com.tutallerPuntoCom.generation.Models.Vehicle;
import com.tutallerPuntoCom.generation.Security.JwtServiceI;
import com.tutallerPuntoCom.generation.DTos.RequestDTO.RegisterUserRequestDTo;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.AuthResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.UserResponseDTO;
import com.tutallerPuntoCom.generation.Exceptions.Customs.EmailAlreadyExistsException;
import com.tutallerPuntoCom.generation.Models.User;
import com.tutallerPuntoCom.generation.Repositories.UserRepository;
import com.tutallerPuntoCom.generation.Services.Contracts.AuthServiceI;
import com.tutallerPuntoCom.generation.Services.Contracts.VehicleServiceI;
import com.tutallerPuntoCom.generation.Utils.Enums.Roles;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class AuthServiceImpl implements AuthServiceI {

    private  final UserRepository userRepository;
    private  final PasswordEncoder passwordEncoder;
    private  final JwtServiceI jwtServiceI;
    private final AuthenticationManager authenticationManager;
    private final VehicleServiceI vehicleServiceI;


    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder
                           ,JwtServiceI jwtServiceI,
                           AuthenticationManager authenticationManager,
                           VehicleServiceI vehicleServiceI
    ){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtServiceI = jwtServiceI;
        this.authenticationManager = authenticationManager;
        this.vehicleServiceI = vehicleServiceI;
    }







    /**
     * MEtodo para registrar un usuario
     * usa contraseña hash para hashear la contraseña con bycript
     * nombre completo,apellido,direccion,correo, contrasenia, telefono, crea vehiculo,rol cliente
     * este metodo debe decvolver un token y datos del uusuario creado:
     * como el email,nnombre completo, correo
     *
     * @param requestDTo
     * @return  UserResponseDTO
     * */

    @Transactional
    @Override
    public RegisterResponseDTO register(RegisterUserRequestDTo requestDTo) {
        boolean findUser = this.userRepository.existsByEmail(requestDTo.email());
        if(findUser){
            throw new EmailAlreadyExistsException("Usuario ya esta registrado");
        }
        User user  = new User();
        user.setName(requestDTo.name());
        user.setLastname(requestDTo.lastname());
        user.setEmail(requestDTo.email().trim().toLowerCase());
        user.setPhone(requestDTo.phone());
        user.setAddress(requestDTo.address());
        user.setPassword(passwordEncoder.encode(requestDTo.password()));
        user.setRole(Roles.CLIENTE);
        User savedUser = userRepository.save(user);
        List<VehicleResponseDTO> vehiclesDTO  = this.vehicleServiceI.createVehicles(requestDTo.vehicles(),savedUser.getId());
        String token = jwtServiceI.generateToken(savedUser);
        return new RegisterResponseDTO(token,UserResponseDTO.from(savedUser),vehiclesDTO);
    }


    @Override
    public AuthResponseDTO login(LoginRequestDTO requestDTO)

    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDTO.email().trim().toLowerCase(),
                        requestDTO.password()
                )
        );
        User user = (User) authentication.getPrincipal();
        String token = jwtServiceI.generateToken(user);
        return new AuthResponseDTO(token, UserResponseDTO.from(user));
    }
}
