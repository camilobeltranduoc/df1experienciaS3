package com.duoc.envios.repository;

import com.duoc.envios.model.Envio;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Acceso a la tabla `envios`.
 */
public interface EnvioRepository extends JpaRepository<Envio, Long> {
    // no necesitas agregar nada más por ahora
}