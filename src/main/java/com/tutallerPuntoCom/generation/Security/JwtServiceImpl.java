package com.tutallerPuntoCom.generation.Security;

import com.tutallerPuntoCom.generation.Models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
@Slf4j
@Service
public class JwtServiceImpl implements JwtServiceI
{

    private final SecretKey key;
    private  final  long expiration;

    public JwtServiceImpl(
            @Value("${jwt.secret}") String key,
            @Value("${jwt.expiration-ms}") long expiration){
        this.expiration = expiration;
        this.key = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String generateToken(User user) {
        Date now = new Date();
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("uid", user.getId())
                .claim("role", user.getRole().name())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expiration))
                .signWith(key)
                .compact();
    }

    @Override
    public Optional<AuthUser> validate(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            Long id = Long.valueOf(claims.get("uid").toString());
            String role = claims.get("role",String.class);
            return  Optional.of(new AuthUser(id,claims.getSubject(),role));

        } catch (ExpiredJwtException e) {
            log.debug("Token expirado");
            return Optional.empty();
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("Token JWT inválido [{}]", e.getClass().getSimpleName());
            return Optional.empty();
        }
    }
}
