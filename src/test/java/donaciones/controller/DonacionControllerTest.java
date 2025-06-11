package donaciones.controller;

import donaciones.dto.request.DonacionRequest;
import donaciones.dto.response.DonacionResponse;
import donaciones.service.DonacionService;
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
public class DonacionControllerTest {

    @Mock
    private DonacionService donacionService;

    @InjectMocks
    private DonacionController donacionController;

    @Test
    void crearDonacion_shouldReturnCreatedResponse() {
        DonacionRequest request = new DonacionRequest();
        DonacionResponse response = new DonacionResponse();
        when(donacionService.crearDonacion(request)).thenReturn(response);

        ResponseEntity<DonacionResponse> result = donacionController.crearDonacion(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void listarPorUsuario_shouldReturnOkResponseWithDonations() {
        Long usuarioId = 1L;
        List<DonacionResponse> donaciones = Collections.singletonList(new DonacionResponse());
        when(donacionService.listarDonacionesPorUsuario(usuarioId)).thenReturn(donaciones);

        ResponseEntity<List<DonacionResponse>> result = donacionController.listarPorUsuario(usuarioId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(donaciones, result.getBody());
    }

    @Test
    void actualizarEstado_shouldReturnOkResponse() {
        Long id = 1L;
        String estado = "Aprobado";
        DonacionResponse response = new DonacionResponse();
        when(donacionService.actualizarEstado(id, estado)).thenReturn(response);

        ResponseEntity<DonacionResponse> result = donacionController.actualizarEstado(id, estado);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void eliminarDonacionPorId_shouldReturnNoContentResponse() {
        Long id = 1L;

        ResponseEntity<Void> result = donacionController.eliminarDonacionPorId(id);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(donacionService, times(1)).eliminarDonacionPorId(id);
    }
}