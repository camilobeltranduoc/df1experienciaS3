package com.duoc.veterinaria.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FacturaNotFoundException extends RuntimeException {
    public FacturaNotFoundException(Long num) {
        super("La factura con número " + num + " no fue encontrada");
    }
}
