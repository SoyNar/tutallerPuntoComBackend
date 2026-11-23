package com.tutallerPuntoCom.generation.Exceptions.Customs;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
