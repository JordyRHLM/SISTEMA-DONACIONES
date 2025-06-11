package donaciones.service;

import donaciones.dto.request.CampaniaRequest;
import donaciones.dto.response.CampaniaRecaudacionResponse;
import donaciones.dto.response.CampaniaResponse;
import donaciones.model.enums.CampaniaEstado;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CampaniaServiceTest {

    @Mock
    private CampaniaService campaniaService;

    @Test
    void crearCampania() {
        CampaniaRequest request = new CampaniaRequest();
        CampaniaResponse expectedResponse = new CampaniaResponse();
        when(campaniaService.crearCampania(request)).thenReturn(expectedResponse);

        CampaniaResponse actualResponse = campaniaService.crearCampania(request);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void obtenerTodas() {
        List<CampaniaResponse> expectedList = Collections.emptyList();
        when(campaniaService.obtenerTodas()).thenReturn(expectedList);

        List<CampaniaResponse> actualList = campaniaService.obtenerTodas();

        assertEquals(expectedList, actualList);
    }

    @Test
    void actualizarCampania() {
        Long id = 1L;
        CampaniaRequest request = new CampaniaRequest();
        CampaniaResponse expectedResponse = new CampaniaResponse();
        when(campaniaService.actualizarCampania(id, request)).thenReturn(expectedResponse);

        CampaniaResponse actualResponse = campaniaService.actualizarCampania(id, request);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void cambiarEstado() {
        Long id = 1L;
        CampaniaEstado estado = CampaniaEstado.ACTIVA;

        campaniaService.cambiarEstado(id, estado);

        verify(campaniaService, times(1)).cambiarEstado(id, estado);
    }

    @Test
    void calcularRecaudacion() {
        Long campaniaId = 1L;
        CampaniaRecaudacionResponse expectedResponse = new CampaniaRecaudacionResponse();
        when(campaniaService.calcularRecaudacion(campaniaId)).thenReturn(expectedResponse);

        CampaniaRecaudacionResponse actualResponse = campaniaService.calcularRecaudacion(campaniaId);

        assertEquals(expectedResponse, actualResponse);
    }
}