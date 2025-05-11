package com.duoc.envios.hateoas;

import com.duoc.envios.controller.EnvioController;
import com.duoc.envios.model.Envio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.List;

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

    @Test
    void toModel_deberiaTenerExactamenteDosLinks() {
        // Arrange
        Envio envio = new Envio(7L, "Carlos", "Argentina", "Buenos Aires", "PENDIENTE");

        // Act
        EntityModel<Envio> model = assembler.toModel(envio);

        // Assert
        assertEquals(2,model.getLinks().stream().count(),"…");
    }

    @Test
    void toCollectionModel_deberiaConservarElOrdenDeEnviosYContenidoCorrecto() {
        // Arrange
        Envio e1 = new Envio(1L, "A", "CL", "SCL", "OK");
        Envio e2 = new Envio(2L, "B", "CL", "Valpo", "OK");

        // Act
        CollectionModel<EntityModel<Envio>> collection = assembler.toCollectionModel(List.of(e1, e2));

        // Assert: los contenidos deben estar en el mismo orden y ser exactamente los objetos originales
        List<Envio> contenidos =
            collection.getContent()
                    .stream()
                    .map(EntityModel::getContent)
                    .toList();

        assertEquals(2, contenidos.size(), "La colección debe contener 2 envíos");
        assertEquals(List.of(e1, e2), contenidos, "El orden y los objetos deben coincidir con la lista original");
    }

    @Test
    void toCollectionModel_cadaModeloIncluyeLinkSelf() {
        // Arrange
        Envio e1 = new Envio(10L, "X", "PE", "Lima", "DESPACHADO");
        Envio e2 = new Envio(11L, "Y", "PE", "Cusco", "ENTREGADO");

        // Act
        CollectionModel<EntityModel<Envio>> collection = assembler.toCollectionModel(List.of(e1, e2));

        // Assert: cada elemento debe tener su propio enlace self correcto
        for (EntityModel<Envio> item : collection) {
            Long id = item.getContent().getId();
            String href = item.getRequiredLink("self").getHref();
            String expected = linkTo(methodOn(EnvioController.class).getById(id)).withSelfRel().getHref();
            assertEquals(expected, href, "Cada modelo debe tener su enlace self apuntando a /envios/{id}");
        }
    }
}
