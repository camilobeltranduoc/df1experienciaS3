package com.duoc.envios.service;

import com.duoc.envios.exception.EnvioNotFoundException;
import com.duoc.envios.model.Envio;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EnvioService {

    private List<Envio> envios = new ArrayList<>();

    public EnvioService() {
        envios.add(new Envio(1, "Juan Pérez", "Chile", "Miami", "En tránsito"));
        envios.add(new Envio(2, "Anna Müller", "Alemania", "Madrid", "En tránsito"));
        envios.add(new Envio(3, "Carlos Ruiz", "México", "Ciudad de Panamá", "En tránsito"));
        envios.add(new Envio(4, "Laura Smith", "Estados Unidos", "Lima", "En tránsito"));
        envios.add(new Envio(5, "Sofía Herrera", "Argentina", "Sao Paulo", "En tránsito"));
        envios.add(new Envio(6, "Hiro Tanaka", "Japón", "San Francisco", "En tránsito"));
    }

    public List<Envio> obtenerTodos() {
        return envios;
    }

    public Envio obtenerPorId(int id) {
        return envios.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EnvioNotFoundException(id));
    }

    public Envio registrarEnvio(Envio envio) {
        envios.add(envio);
        return envio;
    }

    public Envio actualizarEstado(int id, String estado, String ubicacion) {
        Envio envio = obtenerPorId(id);
        envio.setEstado(estado);
        envio.setUbicacionActual(ubicacion);
        return envio;
    }
}
