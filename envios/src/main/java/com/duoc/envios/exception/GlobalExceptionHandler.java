package com.duoc.envios.exception;

import com.duoc.envios.model.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EnvioNotFoundException.class)
    public ResponseEntity<ResponseWrapper<String>> handleNotFound(EnvioNotFoundException ex) {
        ResponseWrapper<String> body =
            new ResponseWrapper<>("ERROR", 0, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    /* Otros manejadores genéricos: */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWrapper<String>> handleGeneric(Exception ex) {
        ResponseWrapper<String> body =
            new ResponseWrapper<>("ERROR", 0, "Error inesperado: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
