package com.tutallerPuntoCom.generation.Repositories;

import com.tutallerPuntoCom.generation.DTos.RequestDTO.UserRequestDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.PageResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.UserResponseDTO;
import com.tutallerPuntoCom.generation.Models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findUserByEmail(String email);

    boolean existsByEmail(String email);

    Page<User> findAllBy(Pageable pageable);
    @EntityGraph(attributePaths = "vehicles")
    Optional<User> findWithVehiclesById(Long id);

}