package com.duoc.veterinaria.hateoas;

import com.duoc.veterinaria.controller.FacturaController;
import com.duoc.veterinaria.model.Factura;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
}
