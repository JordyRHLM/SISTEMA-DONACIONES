package donaciones.controller;

import donaciones.dto.request.OrganizacionRequest;
import donaciones.dto.response.OrganizacionResponse;
import donaciones.service.IOrganizacionService;
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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrganizacionControllerTest {

    @Mock
    private IOrganizacionService organizacionService;

    @InjectMocks
    private OrganizacionController organizacionController;

    @Test
    void listarOrganizaciones_ReturnsOkResponseWithOrganizaciones() {
        when(organizacionService.listar()).thenReturn(Collections.emptyList());
        ResponseEntity<List<OrganizacionResponse>> response = organizacionController.listarOrganizaciones();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void obtenerPorId_ReturnsOkResponseWithOrganizacion() {
        OrganizacionResponse organizacionResponse = new OrganizacionResponse();
        when(organizacionService.obtenerPorId(1L)).thenReturn(organizacionResponse);
        ResponseEntity<OrganizacionResponse> response = organizacionController.obtenerPorId(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void crearOrganizacion_ReturnsOkResponseWithCreatedOrganizacion() {
        OrganizacionRequest request = new OrganizacionRequest();
        OrganizacionResponse organizacionResponse = new OrganizacionResponse();
        when(organizacionService.crear(request)).thenReturn(organizacionResponse);
        ResponseEntity<OrganizacionResponse> response = organizacionController.crearOrganizacion(request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void eliminarOrganizacion_ReturnsNoContentResponse() {
        ResponseEntity<Void> response = organizacionController.eliminarOrganizacion(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(organizacionService, times(1)).eliminar(1L);
    }

    @Test
    void darBaja_ReturnsOkResponseWithOrganizacion() {
        OrganizacionResponse organizacionResponse = new OrganizacionResponse();
        when(organizacionService.darBaja(1L)).thenReturn(organizacionResponse);
        ResponseEntity<OrganizacionResponse> response = organizacionController.darBaja(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}