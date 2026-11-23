package com.tutallerPuntoCom.generation.Security;

import com.tutallerPuntoCom.generation.Models.User;

import java.util.Optional;

public interface JwtServiceI {

    String generateToken(User user);
    Optional<AuthUser> validate(String token);

}
