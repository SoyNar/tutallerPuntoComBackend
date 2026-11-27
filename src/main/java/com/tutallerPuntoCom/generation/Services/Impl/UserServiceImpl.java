package com.tutallerPuntoCom.generation.Services.Impl;

import com.tutallerPuntoCom.generation.DTos.ResponseDTo.PageResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.UserProfileResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.UserResponseDTO;
import com.tutallerPuntoCom.generation.Exceptions.Customs.NotFoundModelException;
import com.tutallerPuntoCom.generation.Models.User;
import com.tutallerPuntoCom.generation.Repositories.UserRepository;
import com.tutallerPuntoCom.generation.Services.Contracts.UserServiceI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserServiceI {

    private final UserRepository userRepo;

    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<UserResponseDTO> all(Pageable pageable) {
        Page<User> page = userRepo.findAllBy(pageable);
        return PageResponseDTO.from(page.map(UserResponseDTO::from));
    }

    @Override
    public UserProfileResponseDTO findById(Long id) {
        User user = userRepo.findWithVehiclesById(id).orElseThrow(() -> new NotFoundModelException("Usuario no encontrado con id " + id));

        return UserProfileResponseDTO.from(user);
    }
}