package com.duoc.envios.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;                 // ← agrega Lombok

import java.time.LocalDateTime;

@Data                               // ← genera getters/setters y toString
@JsonPropertyOrder({ "status", "count", "timestamp", "data" })
public class ResponseWrapper<T> {

    private String status;
    private int count;
    private LocalDateTime timestamp;
    private T data;

    public ResponseWrapper(String status, int count, T data) {
        this.status = status;
        this.count = count;
        this.timestamp = LocalDateTime.now();
        this.data = data;
    }
}