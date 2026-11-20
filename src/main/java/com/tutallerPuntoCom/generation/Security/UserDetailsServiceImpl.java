package com.tutallerPuntoCom.generation.Security;

import com.tutallerPuntoCom.generation.Repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UserRepository userRepo;

    public  UserDetailsServiceImpl(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return this.userRepo.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado" + username));
    }
}
