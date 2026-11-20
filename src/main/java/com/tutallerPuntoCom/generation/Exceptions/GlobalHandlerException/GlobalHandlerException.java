package com.tutallerPuntoCom.generation.Exceptions.GlobalHandlerException;

import com.tutallerPuntoCom.generation.Exceptions.Customs.EmailAlreadyExistsException;
import com.tutallerPuntoCom.generation.Exceptions.Customs.NotFoundModelException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }
        ErrorResponseDTO response = ErrorResponseDTO.withValidationErrors(
                HttpStatus.BAD_REQUEST.value(),
                "Error de validación en los datos enviados",
                fieldErrors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // 1. Correo duplicado al registrarse -> 409 Conflict
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        ErrorResponseDTO response = ErrorResponseDTO.of(
                HttpStatus.CONFLICT.value(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // 2. Credenciales incorrectas en el login -> 401 Unauthorized
    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity<ErrorResponseDTO> handleBadCredentials(Exception ex) {
        ErrorResponseDTO response = ErrorResponseDTO.of(
                HttpStatus.UNAUTHORIZED.value(),
                "Correo o contraseña incorrectos"
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(NotFoundModelException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(NotFoundModelException ex) {
        Map<String, Object> body = Map.of(
                "status", 404,
                "error", "Not Found",
                "message", ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }


}