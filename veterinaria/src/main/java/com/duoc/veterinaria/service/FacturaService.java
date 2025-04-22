package com.duoc.veterinaria.service;

import com.duoc.veterinaria.exception.FacturaNotFoundException;
import com.duoc.veterinaria.model.Factura;
import com.duoc.veterinaria.repository.FacturaRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturaService {

    private final FacturaRepository repo;

    public FacturaService(FacturaRepository repo) {
        this.repo = repo;
    }

    public List<Factura> obtenerTodas() {
        return repo.findAll();
    }

    public Factura obtenerPorNumero(Long n) {
        return repo.findById(n)
                   .orElseThrow(() -> new FacturaNotFoundException(n));
    }

    public Factura crearFactura(@Valid Factura f) {
        return repo.save(f);
    }

    
    public Factura actualizarFactura(Long n, @Valid Factura datos) {
        Factura existente = obtenerPorNumero(n);
        existente.setCliente(datos.getCliente());
        existente.setTotal(datos.getTotal());
        existente.setPagada(datos.isPagada());
        existente.setServicios(datos.getServicios());
        return repo.save(existente);
    }

    
    public void eliminarFactura(Long n) {
        repo.deleteById(n);
    }

    public Factura pagarFactura(Long n) {
        Factura f = obtenerPorNumero(n);
        f.setPagada(true);
        return repo.save(f);
    }
}
