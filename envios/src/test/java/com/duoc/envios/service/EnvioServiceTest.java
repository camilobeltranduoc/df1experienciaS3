package com.duoc.envios.service;

import com.duoc.envios.model.Envio;
import com.duoc.envios.repository.EnvioRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith; 
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnvioServiceTest {

    @Mock
    private EnvioRepository envioRepository;

    private EnvioService envioService;

    @BeforeEach                // se ejecuta antes de cada método @Test
    void setUp() {
        envioService = new EnvioService(envioRepository);
    }

    @AfterEach                 // limpia mocks después de cada @Test
    void tearDown() {
        Mockito.reset(envioRepository);
    }

    @Test
    void registrarEnvio_deberiaRetornarEnvioConId() {
        // Arrange
        Envio sinId = new Envio(null, "Ana", "Chile", "Santiago", "PENDIENTE");
        Envio conId = new Envio(1L, "Ana", "Chile", "Santiago", "PENDIENTE");
        when(envioRepository.save(sinId)).thenReturn(conId);

        // Act
        Envio resultado = envioService.registrarEnvio(sinId);

        // Assert
        assertAll(
            () -> assertNotNull(resultado.getId()),
            () -> assertEquals(1L, resultado.getId())
        );
        verify(envioRepository).save(sinId);
    }

    @Test
    void obtenerTodos_deberiaInvocarFindAll() {
        // Arrange
        when(envioRepository.findAll()).thenReturn(List.of());

        // Act
        List<Envio> lista = envioService.obtenerTodos();

        // Assert
        assertNotNull(lista);
        verify(envioRepository).findAll();
    }
}
