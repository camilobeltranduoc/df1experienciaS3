package com.duoc.veterinaria.service;

import com.duoc.veterinaria.exception.FacturaNotFoundException;
import com.duoc.veterinaria.model.Factura;
import com.duoc.veterinaria.repository.FacturaRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;  
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacturaServiceTest {

    @Mock
    private FacturaRepository repo;

    private FacturaService service;

    @BeforeEach
    void setUp() {
        service = new FacturaService(repo);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(repo);
    }

    @Test
    void crearFactura_deberiaRetornarConNumero() {
        Factura sinNum = new Factura(null, "Ana", 45000, false,
                                     List.of("Consulta", "Vacuna"));
        Factura conNum = new Factura(1L, "Ana", 45000, false,
                                     List.of("Consulta", "Vacuna"));

        when(repo.save(sinNum)).thenReturn(conNum);

        Factura res = service.crearFactura(sinNum);

        assertAll(
            () -> assertNotNull(res.getNumero()),
            () -> assertEquals(1L, res.getNumero())
        );
        verify(repo).save(sinNum);
    }

    @Test
    void obtenerTodas_deberiaInvocarFindAll() {
        when(repo.findAll()).thenReturn(List.of());

        List<Factura> lista = service.obtenerTodas();

        assertNotNull(lista);
        verify(repo).findAll();
    }
    @Test
    void obtenerPorNumero_deberiaRetornarFacturaExistente() {
        // Arrange
        Factura factura = new Factura(5L, "Pedro", 120.0, false, List.of("Consulta"));
        when(repo.findById(5L)).thenReturn(Optional.of(factura));

        // Act
        Factura resultado = service.obtenerPorNumero(5L);

        // Assert
        assertNotNull(resultado);
        assertEquals(5L, resultado.getNumero());
        assertEquals("Pedro", resultado.getCliente());
        verify(repo).findById(5L);
    }

    @Test
    void obtenerPorNumero_deberiaLanzarExcepcionCuandoNoExiste() {
        // Arrange
        when(repo.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        FacturaNotFoundException ex = assertThrows(
            FacturaNotFoundException.class,
            () -> service.obtenerPorNumero(99L)
        );
        assertTrue(ex.getMessage().contains("99"));
        verify(repo).findById(99L);
    }

    @Test
    void eliminar_deberiaInvocarDeleteById() {
        // Arrange
        Long numero = 7L;

        // Act
        service.eliminarFactura(numero);

        // Assert
        verify(repo).deleteById(numero);
    }
}
