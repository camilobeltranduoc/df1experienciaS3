package com.duoc.veterinaria.controller;

import com.duoc.veterinaria.model.Factura;
import com.duoc.veterinaria.model.ResponseWrapper;
import com.duoc.veterinaria.service.FacturaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facturas")
public class FacturaController {

    private final FacturaService svc;

    public FacturaController(FacturaService svc) {
        this.svc = svc;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Factura>>> getAll() {
        var list = svc.obtenerTodas();
        return ResponseEntity.ok(new ResponseWrapper<>("OK", list.size(), list));
    }

    @GetMapping("/{num}")
    public ResponseEntity<ResponseWrapper<Factura>> getByNum(@PathVariable Long num) {
        var f = svc.obtenerPorNumero(num);
        return ResponseEntity.ok(new ResponseWrapper<>("OK", 1, f));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<Factura>> crear(
            @Valid @RequestBody Factura factura) {
        var creada = svc.crearFactura(factura);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(new ResponseWrapper<>("OK", 1, creada));
    }

    
    @PutMapping("/{num}")
    public ResponseEntity<ResponseWrapper<Factura>> actualizar(
            @PathVariable Long num,
            @Valid @RequestBody Factura factura) {
        var actualizada = svc.actualizarFactura(num, factura);
        return ResponseEntity.ok(new ResponseWrapper<>("OK", 1, actualizada));
    }

    
    @DeleteMapping("/{num}")
    public ResponseEntity<ResponseWrapper<Void>> eliminar(@PathVariable Long num) {
        svc.eliminarFactura(num);
        return ResponseEntity.ok(new ResponseWrapper<>("OK", 0, null));
    }

    @PostMapping("/{num}/pagar")
    public ResponseEntity<ResponseWrapper<Factura>> pagar(@PathVariable Long num) {
        var f = svc.pagarFactura(num);
        return ResponseEntity.ok(new ResponseWrapper<>("OK", 1, f));
    }
}
