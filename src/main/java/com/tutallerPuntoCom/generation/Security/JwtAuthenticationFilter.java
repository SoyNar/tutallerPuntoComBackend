package com.tutallerPuntoCom.generation.Security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String PREFIX = "Bearer ";
    private final JwtServiceI jwtServiceI;

    public JwtAuthenticationFilter(JwtServiceI jwtServiceI) {
        this.jwtServiceI = jwtServiceI;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        System.out.println("[JWT] Ruta: " + request.getRequestURI() + " | Header presente: " + (header != null));

        if (header != null && header.startsWith(PREFIX)
                && SecurityContextHolder.getContext().getAuthentication() == null) {

            String token = header.substring(PREFIX.length()).trim();
            Optional<AuthUser> user = jwtServiceI.validate(token);
            System.out.println("[JWT] Token valido: " + user.isPresent());

            if (user.isPresent()) {
                var u = user.get();
                String rawRole = u.role();
                String normalizedRole = rawRole.startsWith("ROLE_") ? rawRole : "ROLE_" + rawRole;
                System.out.println("[JWT] Usuario: " + u.email() + " | Rol: " + normalizedRole);

                var auth = new UsernamePasswordAuthenticationToken(
                        u, null, List.of(new SimpleGrantedAuthority(normalizedRole)));
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        chain.doFilter(request, response);
    }
}