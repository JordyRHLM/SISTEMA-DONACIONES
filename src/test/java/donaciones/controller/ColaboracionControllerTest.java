package donaciones.controller;

import donaciones.dto.request.ColaboracionRequest;
import donaciones.dto.response.ColaboracionResponse;
import donaciones.exception.ValidacionException;
import donaciones.model.enums.RolColaboracion;
import donaciones.service.ColaboracionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ColaboracionControllerTest {

    @Mock
    private ColaboracionService colaboracionService;

    @InjectMocks
    private ColaboracionController colaboracionController;

    @Test
    void crearColaboracion_ReturnsCreated() {
        ColaboracionRequest request = new ColaboracionRequest();
        ColaboracionResponse response = new ColaboracionResponse();
        when(colaboracionService.crearColaboracion(request)).thenReturn(response);

        ResponseEntity<ColaboracionResponse> result = colaboracionController.crearColaboracion(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void listarPorCampania_ReturnsOk() {
        Long campaniaId = 1L;
        List<ColaboracionResponse> responseList = Arrays.asList(new ColaboracionResponse());
        when(colaboracionService.listarPorCampania(campaniaId)).thenReturn(responseList);

        ResponseEntity<List<ColaboracionResponse>> result = colaboracionController.listarPorCampania(campaniaId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseList, result.getBody());
    }

    @Test
    void listarPorUsuario_ReturnsOk() {
        Long usuarioId = 1L;
        List<ColaboracionResponse> responseList = Arrays.asList(new ColaboracionResponse());
        when(colaboracionService.listarPorUsuario(usuarioId)).thenReturn(responseList);

        ResponseEntity<List<ColaboracionResponse>> result = colaboracionController.listarPorUsuario(usuarioId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseList, result.getBody());
    }

    @Test
    void listarTodas_ReturnsOk() {
        List<ColaboracionResponse> responseList = Arrays.asList(new ColaboracionResponse());
        when(colaboracionService.listarTodas()).thenReturn(responseList);

        ResponseEntity<List<ColaboracionResponse>> result = colaboracionController.listarTodas();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseList, result.getBody());
    }

    @Test
    void actualizarColaboracion_ReturnsOk() {
        Long usuarioId = 1L;
        Long campaniaId = 2L;
        RolColaboracion rol = RolColaboracion.PROMOTOR;
        ColaboracionResponse response = new ColaboracionResponse();
        when(colaboracionService.actualizarColaboracion(usuarioId, campaniaId, rol)).thenReturn(response);

        ResponseEntity<ColaboracionResponse> result = colaboracionController.actualizarColaboracion(usuarioId, campaniaId, rol);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void eliminarColaboracion_ReturnsNoContent() {
        Long usuarioId = 1L;
        Long campaniaId = 2L;

        ResponseEntity<Void> result = colaboracionController.eliminarColaboracion(usuarioId, campaniaId);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(colaboracionService).eliminarColaboracion(usuarioId, campaniaId);
    }

    @Test
    void handleValidacionException_ReturnsBadRequest() {
        String mensaje = "Error de validación";
        ValidacionException ex = new ValidacionException(mensaje);

        ResponseEntity<String> result = colaboracionController.handleValidacionException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(mensaje, result.getBody());
    }
}