package com.duoc.veterinaria.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FacturaNotFoundException extends RuntimeException {
    public FacturaNotFoundException(int numero) {
        super("La factura con número " + numero + " no fue encontrada");
    }
}
