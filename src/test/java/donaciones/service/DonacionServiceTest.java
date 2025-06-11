package donaciones.service;

import donaciones.dto.request.DonacionRequest;
import donaciones.dto.response.DonacionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DonacionServiceTest {

    @Mock
    private DonacionService donacionService;

    @Test
    void listarDonacionesPorUsuario_ReturnsEmptyList_WhenNoDonationsExist() {
        when(donacionService.listarDonacionesPorUsuario(1L)).thenReturn(Collections.emptyList());
        List<DonacionResponse> donaciones = donacionService.listarDonacionesPorUsuario(1L);
        assertTrue(donaciones.isEmpty());
    }

    @Test
    void obtenerDonacionPorId_ReturnsNull_WhenDonationNotFound() {
        when(donacionService.obtenerDonacionPorId(1L)).thenReturn(null);
        DonacionResponse donacion = donacionService.obtenerDonacionPorId(1L);
        assertNull(donacion);
    }

    @Test
    void listarTodasLasDonaciones_ReturnsEmptyList_WhenNoDonationsExist() {
        when(donacionService.listarTodasLasDonaciones()).thenReturn(Collections.emptyList());
        List<DonacionResponse> donaciones = donacionService.listarTodasLasDonaciones();
        assertTrue(donaciones.isEmpty());
    }

    @Test
    void actualizarEstado_ReturnsNull_WhenDonationNotFound() {
        when(donacionService.actualizarEstado(1L, "Aprobado")).thenReturn(null);
        DonacionResponse donacionResponse = donacionService.actualizarEstado(1L, "Aprobado");
        assertNull(donacionResponse);
    }

    @Test
    void crearDonacion_ReturnsDonacionResponse() {
        DonacionRequest request = new DonacionRequest();
        DonacionResponse expectedResponse = new DonacionResponse();
        when(donacionService.crearDonacion(request)).thenReturn(expectedResponse);
        DonacionResponse actualResponse = donacionService.crearDonacion(request);
        assertEquals(expectedResponse, actualResponse);
    }
}