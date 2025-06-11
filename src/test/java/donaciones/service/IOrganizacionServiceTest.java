package donaciones.service;

import donaciones.dto.request.OrganizacionRequest;
import donaciones.dto.response.OrganizacionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IOrganizacionServiceTest {

    @Mock
    private IOrganizacionService organizacionService;

    @Test
    void listar() {
        when(organizacionService.listar()).thenReturn(Collections.emptyList());
        List<OrganizacionResponse> result = organizacionService.listar();
        assertTrue(result.isEmpty());
    }

    @Test
    void obtenerPorId() {
        OrganizacionResponse mockResponse = new OrganizacionResponse();
        when(organizacionService.obtenerPorId(1L)).thenReturn(mockResponse);
        OrganizacionResponse result = organizacionService.obtenerPorId(1L);
        assertEquals(mockResponse, result);
    }

    @Test
    void crear() {
        OrganizacionRequest request = new OrganizacionRequest();
        OrganizacionResponse mockResponse = new OrganizacionResponse();
        when(organizacionService.crear(request)).thenReturn(mockResponse);
        OrganizacionResponse result = organizacionService.crear(request);
        assertEquals(mockResponse, result);
    }

    @Test
    void darBaja() {
        OrganizacionResponse mockResponse = new OrganizacionResponse();
        when(organizacionService.darBaja(1L)).thenReturn(mockResponse);
        OrganizacionResponse result = organizacionService.darBaja(1L);
        assertEquals(mockResponse, result);
    }

    @Test
    void eliminar() {
        doNothing().when(organizacionService).eliminar(1L);
        organizacionService.eliminar(1L);
        verify(organizacionService, times(1)).eliminar(1L);
    }
}