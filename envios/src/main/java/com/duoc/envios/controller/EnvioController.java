package com.duoc.envios.controller;

import com.duoc.envios.model.Envio;
import com.duoc.envios.service.EnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/envios")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @GetMapping
    public List<Envio> getAll() {
        return envioService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Envio getById(@PathVariable int id) {
        return envioService.obtenerPorId(id);
    }

    @PostMapping
    public Envio registrar(@RequestBody Envio envio) {
        return envioService.registrarEnvio(envio);
    }

    @PutMapping("/{id}")
    public Envio actualizar(@PathVariable int id,
                            @RequestParam String estado,
                            @RequestParam String ubicacion) {
        return envioService.actualizarEstado(id, estado, ubicacion);
    }
}
