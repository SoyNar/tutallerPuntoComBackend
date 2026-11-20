package com.tutallerPuntoCom.generation.Config;

import com.tutallerPuntoCom.generation.Security.AuthUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


/**
 * Configuración de seguridad exclusiva para el entorno de desarrollo local ({@code @Profile("dev")}).
 *
 * <p>Permite probar los endpoints sin necesidad de autenticarse ni enviar tokens JWT en las peticiones.
 * Para evitar errores de {@link NullPointerException} en los controladores que esperan un usuario autenticado
 * mediante {@code @AuthenticationPrincipal}, registra un filtro simulado ({@link #mockAuthFilter()}) que inyecta
 * automáticamente un {@link AuthUser} de prueba en el {@link SecurityContextHolder}.</p>
 *
 * <p><strong>Nota para el equipo:</strong></p>
 * <ul>
 *   <li>Esta clase se desactiva automáticamente cuando el perfil activo es distinto de {@code dev} (por ejemplo, en {@code prod}),
 *   dando paso a {@link SecurityFilterConfig} con la autenticación JWT real.</li>
 *   <li>Asegúrate de que el ID configurado en el usuario simulado exista en tu base de datos local.</li>
 *   <li>Antes de dar una funcionalidad por terminada, prueba siempre con el perfil {@code prod} y un token real
 *   para validar permisos y roles.</li>
 * </ul>
 */
@Configuration
@Profile("dev")
public class DevSecurity {

    @Bean
    public SecurityFilterChain devSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .addFilterBefore(mockAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public OncePerRequestFilter mockAuthFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain filterChain) throws ServletException, IOException {
                AuthUser fakeUser = new AuthUser(1L, "dev@tutaller.com", "ADMIN");

                var auth = new UsernamePasswordAuthenticationToken(
                        fakeUser,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
                );

                SecurityContextHolder.getContext().setAuthentication(auth);
                filterChain.doFilter(request, response);
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}