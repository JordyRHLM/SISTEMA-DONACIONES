package donaciones.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void testUsuarioBuilder() {
        Usuario usuario = Usuario.builder()
                .email("test@example.com")
                .nombre("Test User")
                .password("password")
                .isAdmin(true)
                .isOrgOwner(false)
                .organizaciones(new ArrayList<>())
                .donaciones(new ArrayList<>())
                .build();

        assertEquals("test@example.com", usuario.getEmail());
        assertEquals("Test User", usuario.getNombre());
        assertTrue(usuario.getIsAdmin());
        assertFalse(usuario.getIsOrgOwner());
    }

    @Test
    void testUsuarioDefaultCreatedAt() {
        Usuario usuario = new Usuario();
        assertNotNull(usuario.getCreatedAt());
    }

    @Test
    void testUsuarioPrePersist() {
        Usuario usuario = new Usuario();
        LocalDateTime before = LocalDateTime.now();
        usuario.onCreate();
        LocalDateTime after = LocalDateTime.now();
        assertTrue(usuario.getCreatedAt().isAfter(before.minusSeconds(1)) && usuario.getCreatedAt().isBefore(after.plusSeconds(1)));
    }

    @Test
    void testUsuarioSettersAndGetters() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("test@example.com");
        usuario.setNombre("Test User");
        usuario.setPassword("password");
        usuario.setIsAdmin(true);
        usuario.setIsOrgOwner(true);

        assertEquals(1L, usuario.getId());
        assertEquals("test@example.com", usuario.getEmail());
        assertEquals("Test User", usuario.getNombre());
        assertTrue(usuario.getIsAdmin());
        assertTrue(usuario.getIsOrgOwner());
    }

    @Test
    void testNoArgsConstructor() {
        Usuario usuario = new Usuario();
        assertNull(usuario.getId());
        assertNull(usuario.getEmail());
        assertNull(usuario.getNombre());
        assertNull(usuario.getPassword());
        assertFalse(usuario.getIsAdmin());
        assertFalse(usuario.getIsOrgOwner());
        assertNotNull(usuario.getCreatedAt());
    }
}