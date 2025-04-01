package com.duoc.envios.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EnvioNotFoundException.class)
    public ResponseEntity<Object> handleEnvioNotFound(EnvioNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
