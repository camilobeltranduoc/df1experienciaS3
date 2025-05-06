package com.duoc.envios.hateoas;

import com.duoc.envios.controller.EnvioController;
import com.duoc.envios.model.Envio;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EnvioModelAssembler 
        implements RepresentationModelAssembler<Envio, EntityModel<Envio>> {

    @Override
    public EntityModel<Envio> toModel(Envio envio) {
        return EntityModel.of(envio,
            linkTo(methodOn(EnvioController.class).getById(envio.getId())).withSelfRel(),
            linkTo(methodOn(EnvioController.class).getAll()).withRel("envios")
        );
    }
}