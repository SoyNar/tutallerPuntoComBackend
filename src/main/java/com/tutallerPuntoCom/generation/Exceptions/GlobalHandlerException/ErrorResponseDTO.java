package com.tutallerPuntoCom.generation.Exceptions.GlobalHandlerException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponseDTO
        (LocalDateTime dateTime, int status, String message, Map<String,String> errors)
{
    public static ErrorResponseDTO of(int status,String message){
        return new ErrorResponseDTO(LocalDateTime.now(),status,message,null);
    }

    public static ErrorResponseDTO withValidationErrors(int status, String message, Map<String, String> errors) {
        return new ErrorResponseDTO(LocalDateTime.now(), status, message, errors);
    }
}
