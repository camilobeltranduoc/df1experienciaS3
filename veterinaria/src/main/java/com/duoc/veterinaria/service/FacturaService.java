package com.duoc.veterinaria.service;

import com.duoc.veterinaria.exception.FacturaNotFoundException;
import com.duoc.veterinaria.model.Factura;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FacturaService {
    private List<Factura> facturas = new ArrayList<>();

    public FacturaService() {
        facturas.add(new Factura(1, "Camila Soto", Arrays.asList("Consulta", "Vacuna"), 28000, false));
        facturas.add(new Factura(2, "Luis Rivera", Arrays.asList("Radiografía"), 32000, true));
        facturas.add(new Factura(3, "Fernanda Díaz", Arrays.asList("Baño", "Desparasitación"), 15000, false));
        facturas.add(new Factura(4, "Roman Cabrera", Arrays.asList("Consulta", "Desparasitación"), 45000, false));
        facturas.add(new Factura(5, "Juan Valdez", Arrays.asList("Baño", "Desparasitación"), 15000, false));
    }

    public List<Factura> obtenerTodas() {
        return facturas;
    }

    public Factura obtenerPorNumero(int numero) {
    return facturas.stream()
            .filter(f -> f.getNumero() == numero)
            .findFirst()
            .orElseThrow(() -> new FacturaNotFoundException(numero));
}

public String pagarFactura(int numero) {
    Factura factura = obtenerPorNumero(numero);

    if (factura.isPagada()) {
        return "La factura ya fue pagada.";
    }

    factura.setPagada(true);
    return "Factura número " + numero + " pagada correctamente.";
}
}
