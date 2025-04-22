package com.duoc.envios.controller;

import com.duoc.envios.model.Envio;
import com.duoc.envios.model.ResponseWrapper;          // <-- asegúrate de este import
import com.duoc.envios.service.EnvioService;          // <-- nombre correcto de la clase
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;        // <-- faltaba
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/envios")
public class EnvioController {

    private final EnvioService envioService;

    public EnvioController(EnvioService envioService) {
        this.envioService = envioService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Envio>>> getAll() {  
        List<Envio> lista = envioService.obtenerTodos();
        ResponseWrapper<List<Envio>> body = new ResponseWrapper<>("OK", lista.size(), lista);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Envio>> getById(@PathVariable Long id) {
        Envio envio = envioService.obtenerPorId(id);
        ResponseWrapper<Envio> body = new ResponseWrapper<>("OK", 1, envio);
        return ResponseEntity.ok(body);
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<Envio>> registrar(@RequestBody Envio envio) {
        Envio creado = envioService.registrarEnvio(envio);
        ResponseWrapper<Envio> body = new ResponseWrapper<>("OK", 1, creado);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Envio>> actualizar(
            @PathVariable Long id,
            @RequestParam String estado,
            @RequestParam String ubicacion) {

        Envio actualizado = envioService.actualizarEstado(id, estado, ubicacion);
        ResponseWrapper<Envio> body = new ResponseWrapper<>("OK", 1, actualizado);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> eliminar(@PathVariable Long id) {
        envioService.eliminar(id);
        ResponseWrapper<Void> body = new ResponseWrapper<>("OK", 0, null);
        return ResponseEntity.ok(body);
    }
}
