package com.duoc.envios.controller;

import com.duoc.envios.hateoas.EnvioModelAssembler;
import com.duoc.envios.model.Envio;
import com.duoc.envios.service.EnvioService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/envios")
public class EnvioController {

    private final EnvioService envioService;
    private final EnvioModelAssembler assembler;

    public EnvioController(EnvioService envioService, EnvioModelAssembler assembler) {
        this.envioService = envioService;
        this.assembler = assembler;
    }

    // GET /envios
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Envio>>> getAll() {
        List<EntityModel<Envio>> lista = envioService.obtenerTodos().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        CollectionModel<EntityModel<Envio>> collection = CollectionModel.of(lista,
            linkTo(methodOn(EnvioController.class).getAll()).withSelfRel()
        );

        return ResponseEntity.ok(collection);
    }

    // GET /envios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Envio>> getById(@PathVariable Long id) {
        Envio envio = envioService.obtenerPorId(id);
        EntityModel<Envio> model = assembler.toModel(envio);
        return ResponseEntity.ok(model);
    }

    // POST /envios
    @PostMapping
    public ResponseEntity<EntityModel<Envio>> registrar(@RequestBody Envio envio) {
        Envio creado = envioService.registrarEnvio(envio);
        EntityModel<Envio> model = assembler.toModel(creado);

        // Construye URI a /envios/{id}
        URI uri = linkTo(methodOn(EnvioController.class).getById(creado.getId())).toUri();
        return ResponseEntity.created(uri).body(model);
    }

    // PUT /envios/{id}?estado=&ubicacion=
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Envio>> actualizar(
        @PathVariable Long id,
        @RequestParam String estado,
        @RequestParam String ubicacion) {

        Envio actualizado = envioService.actualizarEstado(id, estado, ubicacion);
        EntityModel<Envio> model = assembler.toModel(actualizado);
        return ResponseEntity.ok(model);
    }

    // DELETE /envios/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        envioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}