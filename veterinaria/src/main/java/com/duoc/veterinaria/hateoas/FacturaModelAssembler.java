package com.duoc.veterinaria.hateoas;

import com.duoc.veterinaria.controller.FacturaController;
import com.duoc.veterinaria.model.Factura;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class FacturaModelAssembler
        implements RepresentationModelAssembler<Factura, EntityModel<Factura>> {

    @Override
    public EntityModel<Factura> toModel(Factura factura) {
        return EntityModel.of(factura,
            linkTo(methodOn(FacturaController.class)
                    .getByNum(factura.getNumero()))
                .withSelfRel(),
            linkTo(methodOn(FacturaController.class)
                    .getAll())
                .withRel("facturas"),
            linkTo(methodOn(FacturaController.class)
                    .pagar(factura.getNumero()))
                .withRel("pagar")        // enlace extra opcional
        );
    }
}