package donaciones.controller;

import donaciones.dto.request.RegisterRequest;
import donaciones.dto.response.UsuarioResponse;
import donaciones.service.IUsuarioService;
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
class UsuarioControllerTest {

    @Mock
    private IUsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @Test
    void getCurrentUsuario_ReturnsOkResponse() {
        UsuarioResponse mockResponse = new UsuarioResponse();
        when(usuarioService.getCurrentUsuario()).thenReturn(mockResponse);

        ResponseEntity<UsuarioResponse> response = usuarioController.getCurrentUsuario();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
    }

    @Test
    void getUsuarioById_ReturnsOkResponse() {
        Long id = 1L;
        UsuarioResponse mockResponse = new UsuarioResponse();
        when(usuarioService.getUsuarioById(id)).thenReturn(mockResponse);

        ResponseEntity<UsuarioResponse> response = usuarioController.getUsuarioById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
    }

    @Test
    void getAllUsuarios_ReturnsOkResponse() {
        List<UsuarioResponse> mockList = Collections.singletonList(new UsuarioResponse());
        when(usuarioService.getAllUsuarios()).thenReturn(mockList);

        ResponseEntity<List<UsuarioResponse>> response = usuarioController.getAllUsuarios();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockList, response.getBody());
    }

    @Test
    void createUsuario_ReturnsOkResponse() {
        RegisterRequest request = new RegisterRequest();
        UsuarioResponse mockResponse = new UsuarioResponse();
        when(usuarioService.createUsuario(request)).thenReturn(mockResponse);

        ResponseEntity<UsuarioResponse> response = usuarioController.createUsuario(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
    }

    @Test
    void deleteUsuario_ReturnsNoContentResponse() {
        Long id = 1L;

        ResponseEntity<Void> response = usuarioController.deleteUsuario(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(usuarioService, times(1)).deleteUsuario(id);
    }
}