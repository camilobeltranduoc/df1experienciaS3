package com.duoc.veterinaria.hateoas;

import com.duoc.veterinaria.controller.FacturaController;
import com.duoc.veterinaria.model.Factura;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

class FacturaModelAssemblerTest {

    private FacturaModelAssembler assembler;

    @BeforeEach
    void setUp() {
        assembler = new FacturaModelAssembler();
    }

    @Test
    void toModel_incluyeSelfFacturasYPagar() {
        Factura factura = new Factura(
                42L, "Luis", 60000, true,
                List.of("Radiografía", "Cirugía"));

        EntityModel<Factura> model = assembler.toModel(factura);

        String selfHref = linkTo(methodOn(FacturaController.class)
                                   .getByNum(42L))
                          .withSelfRel().getHref();

        assertEquals(selfHref, model.getRequiredLink("self").getHref(),
                     "Debe contener enlace self correcto");

        assertTrue(model.getLink("facturas").isPresent(),
                   "Debe contener enlace a la colección");
        assertTrue(model.getLink("pagar").isPresent(),
                   "Debe contener enlace para pagar");

        assertEquals(42L, model.getContent().getNumero());
    }
    @Test
    void toModel_deberiaIncluirTresLinks() {
        // Arrange
        Factura factura = new Factura(10L, "Cliente", 100.0, false, List.of("Servicio"));

        // Act
        EntityModel<Factura> model = assembler.toModel(factura);

        // Assert: self, facturas y pagar
        assertEquals(
            3,
            model.getLinks().toList().size(),
            "Debe incluir exactamente 3 enlaces: self, facturas y pagar"
        );
    }

    @Test
    void toModel_deberiaIncluirLinkPagarConRutaCorrecta() {
        // Arrange
        long num = 11L;
        Factura factura = new Factura(num, "Cliente2", 200.0, true, List.of("Otro"));

        // Act
        EntityModel<Factura> model = assembler.toModel(factura);

        // Assert: tiene rel="pagar"
        assertTrue(model.hasLink("pagar"), "Debe contener el enlace 'pagar'");

        // Extraemos el href y validamos que apunte a /facturas/{num}/pagar
        String href = model.getRequiredLink("pagar").getHref();
        assertTrue(
            href.endsWith("/facturas/" + num + "/pagar"),
            "El enlace 'pagar' debe terminar en '/facturas/" + num + "/pagar' pero fue: " + href
        );
    }
    @Test
    void toCollectionModel_elementos_noDeberiaTenerLinkSelfPeroElementosTienenLinks() {
        // Arrange
        Factura f1 = new Factura(1L, "A", 50.0, false, List.of("S1"));
        Factura f2 = new Factura(2L, "B", 75.0, true,  List.of("S2"));

        // Act
        CollectionModel<EntityModel<Factura>> coll = assembler.toCollectionModel(List.of(f1, f2));

        // Assert: la colección NO añade un link self
        assertFalse(coll.hasLink("self"), "La colección no debe tener enlace self");

        // Compruebo que contenemos exactamente 2 elementos
        assertEquals(2, coll.getContent().size(), "Debe contener dos elementos");

        // Cada elemento debe tener su propio self y pagar
        coll.forEach(entity -> {
            long numero = entity.getContent().getNumero();
            assertTrue(entity.hasLink("self"),  "Cada factura debe tener su enlace 'self'");
            assertTrue(entity.hasLink("pagar"), "Cada factura debe tener su enlace 'pagar'");

            // Verifico además que el href de self sea correcto
            String expectedSelf = linkTo(methodOn(FacturaController.class)
                    .getByNum(numero))
                .withSelfRel()
                .getHref();
            assertEquals(expectedSelf,
                         entity.getRequiredLink("self").getHref(),
                         "El enlace 'self' de la factura " + numero + " es incorrecto");
        });
    }
    @Test
    void toCollectionModel_vacia_noDeberiaTenerLinkSelf() {
        // Act
        CollectionModel<EntityModel<Factura>> emptyColl = assembler.toCollectionModel(List.of());

        // Assert: sin elementos y sin enlace self en la colección
        assertTrue(emptyColl.getContent().isEmpty(), "La colección no debe contener ningún elemento");
        assertFalse(emptyColl.hasLink("self"), "La colección vacía no debe tener enlace self");
    }

}
