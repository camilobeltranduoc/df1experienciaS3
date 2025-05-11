package com.duoc.envios.service;

import com.duoc.envios.exception.EnvioNotFoundException;
import com.duoc.envios.model.Envio;
import com.duoc.envios.repository.EnvioRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith; 
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

    @Test
    void obtenerPorId_deberiaRetornarEnvioExistente() {
        // Arrange
        Envio envio = new Envio(1L, "Pedro", "Chile", "Valparaíso", "CREADO");
        when(envioRepository.findById(1L)).thenReturn(Optional.of(envio));

        // Act
        Envio resultado = envioService.obtenerPorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Pedro", resultado.getDestinatario());
        verify(envioRepository).findById(1L);
    }

    @Test
    void obtenerPorId_deberiaLanzarExcepcionCuandoNoExiste() {
        // Arrange
        when(envioRepository.findById(42L)).thenReturn(Optional.empty());

        // Act & Assert
        EnvioNotFoundException ex = assertThrows(
            EnvioNotFoundException.class,
            () -> envioService.obtenerPorId(42L)
        );
        assertTrue(ex.getMessage().contains("42"));
        verify(envioRepository).findById(42L);
    }

    @Test
    void actualizarEstado_deberiaActualizarYGuardarEnvio() {
        // Arrange
        Envio original = new Envio(2L, "María", "Perú", "Lima", "PENDIENTE");
        Envio actualizado = new Envio(2L, "María", "Perú", "Cusco", "EN RUTA");
        when(envioRepository.findById(2L)).thenReturn(Optional.of(original));
        when(envioRepository.save(any(Envio.class))).thenReturn(actualizado);

        // Act
        Envio resultado = envioService.actualizarEstado(2L, "EN RUTA", "Cusco");

        // Assert
        assertEquals(2L, resultado.getId());
        assertEquals("EN RUTA", resultado.getEstado());
        assertEquals("Cusco", resultado.getUbicacionActual());
        verify(envioRepository).findById(2L);
        verify(envioRepository).save(original);
    }
}
