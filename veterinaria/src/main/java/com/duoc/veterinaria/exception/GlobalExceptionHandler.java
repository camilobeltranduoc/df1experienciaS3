package com.duoc.veterinaria.exception;

import com.duoc.veterinaria.model.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FacturaNotFoundException.class)
    public ResponseEntity<ResponseWrapper<String>> notFound(FacturaNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseWrapper<>("ERROR", 0, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWrapper<String>> generic(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseWrapper<>("ERROR", 0, "Error: " + ex.getMessage()));
    }
}
