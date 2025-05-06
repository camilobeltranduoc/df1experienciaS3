package com.duoc.envios.hateoas;

import com.duoc.envios.controller.EnvioController;
import com.duoc.envios.model.Envio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

public class EnvioModelAssemblerTest {

    private EnvioModelAssembler assembler;

    @BeforeEach
    void setUp() {
        assembler = new EnvioModelAssembler();
    }

    @Test
    void toModel_deberiaIncluirEnlacesSelfYEnvios() {
        // Arrange
        Envio envio = new Envio(42L, "Luis", "Perú", "Lima", "ENTREGADO");

        // Act
        EntityModel<Envio> model = assembler.toModel(envio);

        // Assert: enlace self
        assertEquals(
            linkTo(methodOn(EnvioController.class).getById(42L)).withSelfRel().getHref(),
            model.getRequiredLink("self").getHref()
        );

        // Assert: enlace a la colección
        assertEquals(
            linkTo(methodOn(EnvioController.class).getAll()).withRel("envios").getHref(),
            model.getRequiredLink("envios").getHref()
        );

        // El contenido debe ser el mismo Envio
        assertEquals(42L, model.getContent().getId());
    }
}
