package com.duoc.veterinaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numero;

    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String cliente;

    @Min(value = 1, message = "El total debe ser positivo")
    private double total;

    private boolean pagada;

    @ElementCollection
    @CollectionTable(
            name = "factura_servicios",
            joinColumns = @JoinColumn(name = "factura_numero")
    )
    @Column(name = "servicio")
    @NotEmpty(message = "Debe haber al menos un servicio")
    private List<@NotBlank String> servicios;
}
