package com.duoc.envios.service;

import com.duoc.envios.exception.EnvioNotFoundException;
import com.duoc.envios.model.Envio;
import com.duoc.envios.repository.EnvioRepository;      // <-- IMPORTA el repo
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnvioService {

    private final EnvioRepository repo;

    public EnvioService(EnvioRepository repo) {
        this.repo = repo;
    }

    /* ------ LISTAR TODOS ------ */
    public List<Envio> obtenerTodos() {
        return repo.findAll();
    }

    /* ------ OBTENER POR ID ------ */
    public Envio obtenerPorId(Long id) {
        return repo.findById(id)
                   .orElseThrow(() -> new EnvioNotFoundException(id));
    }

    /* ------ CREAR ------ */
    public Envio registrarEnvio(Envio envio) {
        return repo.save(envio);
    }

    /* ------ ACTUALIZAR ESTADO ------ */
    public Envio actualizarEstado(Long id, String estado, String ubicacion) {
        Envio envio = obtenerPorId(id);
        envio.setEstado(estado);
        envio.setUbicacionActual(ubicacion);
        return repo.save(envio);
    }

    /* ------ ELIMINAR ------ */
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
