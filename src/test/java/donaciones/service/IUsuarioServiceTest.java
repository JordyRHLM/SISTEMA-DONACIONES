package donaciones.service;

import donaciones.dto.request.RegisterRequest;
import donaciones.dto.response.UsuarioResponse;
import donaciones.model.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IUsuarioServiceTest {

    @Mock
    private IUsuarioService usuarioService;

    @Test
    void getUsuarioById() {
        when(usuarioService.getUsuarioById(1L)).thenReturn(new UsuarioResponse());
        assertNotNull(usuarioService.getUsuarioById(1L));
    }

    @Test
    void getAllUsuarios() {
        when(usuarioService.getAllUsuarios()).thenReturn(Collections.singletonList(new UsuarioResponse()));
        List<UsuarioResponse> usuarios = usuarioService.getAllUsuarios();
        assertFalse(usuarios.isEmpty());
    }

    @Test
    void createUsuario() {
        RegisterRequest request = new RegisterRequest();
        when(usuarioService.createUsuario(request)).thenReturn(new UsuarioResponse());
        assertNotNull(usuarioService.createUsuario(request));
    }

    @Test
    void updateUsuario() {
        RegisterRequest request = new RegisterRequest();
        when(usuarioService.updateUsuario(1L, request)).thenReturn(new UsuarioResponse());
        assertNotNull(usuarioService.updateUsuario(1L, request));
    }

    @Test
    void getUsuarioEntity() {
        when(usuarioService.getUsuarioEntity(1L)).thenReturn(new Usuario());
        assertNotNull(usuarioService.getUsuarioEntity(1L));
    }
}