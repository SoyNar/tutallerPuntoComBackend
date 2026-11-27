package com.tutallerPuntoCom.generation.Controllers;

import com.tutallerPuntoCom.generation.DTos.ResponseDTo.PageResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.UserProfileResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.UserResponseDTO;
import com.tutallerPuntoCom.generation.Security.AuthUser;
import com.tutallerPuntoCom.generation.Services.Contracts.UserServiceI;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceI userServiceI;


    public UserController(UserServiceI userServiceI){
        this.userServiceI = userServiceI;
    }


    @GetMapping
    public ResponseEntity<PageResponseDTO<UserResponseDTO>> getAllUsers(
            @ParameterObject @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(userServiceI.all(pageable));
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponseDTO> getMe(@AuthenticationPrincipal AuthUser authUser)
    {
        UserProfileResponseDTO  responseDTO = this.userServiceI.findById(authUser.id());
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}
