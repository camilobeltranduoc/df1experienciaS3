package com.duoc.veterinaria.controller;

import com.duoc.veterinaria.model.Factura;
import com.duoc.veterinaria.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @GetMapping
    public List<Factura> getAll() {
        return facturaService.obtenerTodas();
    }

    @GetMapping("/{numero}")
    public Factura getByNumero(@PathVariable int numero) {
        return facturaService.obtenerPorNumero(numero);
    }

    @PostMapping("/{numero}/pagar")
    public String pagar(@PathVariable int numero) {
        return facturaService.pagarFactura(numero);
    }
}
