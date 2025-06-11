package donaciones.controller;

import donaciones.dto.request.CampaniaRequest;
import donaciones.dto.response.CampaniaRecaudacionResponse;
import donaciones.dto.response.CampaniaResponse;
import donaciones.service.CampaniaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CampaniaControllerTest {

    @Mock
    private CampaniaService campaniaService;

    @InjectMocks
    private CampaniaController campaniaController;

    @Test
    void obtenerTodasLasCampanias_ReturnsOkResponse() {
        when(campaniaService.obtenerTodas()).thenReturn(Collections.emptyList());
        ResponseEntity<List<CampaniaResponse>> response = campaniaController.obtenerTodasLasCampanias();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void obtenerCampaniaPorId_ReturnsOkResponse() {
        CampaniaResponse mockResponse = new CampaniaResponse();
        when(campaniaService.obtenerPorId(1L)).thenReturn(mockResponse);
        ResponseEntity<CampaniaResponse> response = campaniaController.obtenerCampaniaPorId(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void crearCampania_ReturnsOkResponse() {
        CampaniaRequest request = new CampaniaRequest();
        CampaniaResponse mockResponse = new CampaniaResponse();
        when(campaniaService.crearCampania(request)).thenReturn(mockResponse);
        ResponseEntity<CampaniaResponse> response = campaniaController.crearCampania(request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void eliminarCampania_ReturnsNoContentResponse() {
        ResponseEntity<Void> response = campaniaController.eliminarCampania(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(campaniaService, times(1)).eliminarCampania(1L);
    }

    @Test
    void obtenerRecaudacion_ReturnsOkResponse() {
        CampaniaRecaudacionResponse mockResponse = new CampaniaRecaudacionResponse();
        when(campaniaService.calcularRecaudacion(1L)).thenReturn(mockResponse);
        ResponseEntity<CampaniaRecaudacionResponse> response = campaniaController.obtenerRecaudacion(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}