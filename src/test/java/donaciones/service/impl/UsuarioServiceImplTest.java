package donaciones.service.impl;

import donaciones.dto.request.RegisterRequest;
import donaciones.dto.response.UsuarioResponse;
import donaciones.exception.RecursoNoEncontradoException;
import donaciones.model.Usuario;
import donaciones.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Test
    void getUsuarioById_ExistingUser_ReturnsUsuarioResponse() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Test User");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        UsuarioResponse response = usuarioService.getUsuarioById(1L);

        assertEquals(1L, response.getId());
        assertEquals("Test User", response.getNombre());
    }

    @Test
    void getUsuarioById_NonExistingUser_ThrowsRecursoNoEncontradoException() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> usuarioService.getUsuarioById(1L));
    }

    @Test
    void getAllUsuarios_ReturnsListOfUsuarioResponse() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        when(usuarioRepository.findAll()).thenReturn(Collections.singletonList(usuario));

        List<UsuarioResponse> responses = usuarioService.getAllUsuarios();

        assertEquals(1, responses.size());
        assertEquals(1L, responses.get(0).getId());
    }

    @Test
    void deleteUsuario_ExistingUser_DeletesUser() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        usuarioService.deleteUsuario(1L);

        verify(usuarioRepository, times(1)).delete(usuario);
    }

    @Test
    void createUsuario_ValidRequest_CreatesUser() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setName("Test User");
        request.setPassword("password");

        when(usuarioRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> {
            Usuario savedUsuario = invocation.getArgument(0);
            savedUsuario.setId(1L);
            return savedUsuario;
        });

        UsuarioResponse response = usuarioService.createUsuario(request);

        assertEquals("test@example.com", response.getEmail());
        assertEquals("Test User", response.getNombre());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }
}