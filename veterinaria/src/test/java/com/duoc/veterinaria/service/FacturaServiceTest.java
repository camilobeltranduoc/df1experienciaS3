package com.duoc.veterinaria.service;

import com.duoc.veterinaria.model.Factura;
import com.duoc.veterinaria.repository.FacturaRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;  
import java.util.List;

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
}
