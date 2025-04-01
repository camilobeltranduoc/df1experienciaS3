package com.duoc.envios.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EnvioNotFoundException extends RuntimeException {
    public EnvioNotFoundException(int id) {
        super("El envío con ID " + id + " no fue encontrado.");
    }
}
