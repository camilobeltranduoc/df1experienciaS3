package com.duoc.envios.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity                      
@Table(name = "envios")      
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destinatario;
    private String paisDestino;
    private String ubicacionActual;
    private String estado;
}