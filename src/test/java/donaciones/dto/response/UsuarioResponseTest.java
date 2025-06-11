package donaciones.dto.response;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioResponseTest {

    @Test
    void testUsuarioResponseBuilder() {
        UsuarioResponse response = UsuarioResponse.builder()
                .id(1L)
                .nombre("Test User")
                .email("test@example.com")
                .isAdmin(true)
                .isOrgOwner(false)
                .createdAt(LocalDateTime.now())
                .build();

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Test User", response.getNombre());
        assertEquals("test@example.com", response.getEmail());
        assertTrue(response.getIsAdmin());
        assertFalse(response.getIsOrgOwner());
        assertNotNull(response.getCreatedAt());
    }

    @Test
    void testUsuarioResponseNoArgsConstructor() {
        UsuarioResponse response = new UsuarioResponse();
        assertNotNull(response);
    }

    @Test
    void testUsuarioResponseAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        UsuarioResponse response = new UsuarioResponse(1L, "Test User", "test@example.com", true, false, now);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Test User", response.getNombre());
        assertEquals("test@example.com", response.getEmail());
        assertTrue(response.getIsAdmin());
        assertFalse(response.getIsOrgOwner());
        assertEquals(now, response.getCreatedAt());
    }

    @Test
    void testUsuarioResponseSettersAndGetters() {
        UsuarioResponse response = new UsuarioResponse();
        response.setId(1L);
        response.setNombre("Test User");
        response.setEmail("test@example.com");
        response.setIsAdmin(true);
        response.setIsOrgOwner(false);
        response.setCreatedAt(LocalDateTime.now());

        assertEquals(1L, response.getId());
        assertEquals("Test User", response.getNombre());
        assertEquals("test@example.com", response.getEmail());
        assertTrue(response.getIsAdmin());
        assertFalse(response.getIsOrgOwner());
        assertNotNull(response.getCreatedAt());
    }

    @Test
    void testUsuarioResponseEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();
        UsuarioResponse response1 = new UsuarioResponse(1L, "Test User", "test@example.com", true, false, now);
        UsuarioResponse response2 = new UsuarioResponse(1L, "Test User", "test@example.com", true, false, now);

        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }
}