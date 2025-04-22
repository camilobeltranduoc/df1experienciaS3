package com.duoc.veterinaria.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonPropertyOrder({ "status", "count", "timestamp", "data" })
public class ResponseWrapper<T> {
    private final String status;
    private final int count;
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final T data;
}
