package donaciones.dto.response;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrganizacionResponseTest {

    @Test
    void testNoArgsConstructor() {
        OrganizacionResponse response = new OrganizacionResponse();
        assertNotNull(response);
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();

        OrganizacionResponse response = new OrganizacionResponse();

        response.setId(1L);
        response.setNombre("Test Org");
        response.setDescripcion("Description");
        response.setEstado("Active");
        response.setOwner_id(2L);
        response.setCreatedAt(now);

        assertEquals(1L, response.getId());
        assertEquals("Test Org", response.getNombre());
        assertEquals("Description", response.getDescripcion());
        assertEquals("Active", response.getEstado());
        assertEquals(2L, response.getOwner_id());
        assertEquals(now, response.getCreatedAt());
    }



    @Test
    void testGettersAndSetters() {
        OrganizacionResponse response = new OrganizacionResponse();
        response.setId(1L);
        response.setNombre("Test Org");
        response.setDescripcion("Description");
        response.setEstado("Active");
        response.setOwner_id(2L);
        response.setCreatedAt(LocalDateTime.now());

        assertEquals(1L, response.getId());
        assertEquals("Test Org", response.getNombre());
        assertEquals("Description", response.getDescripcion());
        assertEquals("Active", response.getEstado());
        assertEquals(2L, response.getOwner_id());
        assertNotNull(response.getCreatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();

        OrganizacionResponse response1 = new OrganizacionResponse();
        response1.setId(1L);
        response1.setNombre("Test Org");
        response1.setDescripcion("Description");
        response1.setEstado("Active");
        response1.setOwner_id(2L);
        response1.setCreatedAt(now);

        assertEquals(response1, response1);
        assertEquals(response1.hashCode(), response1.hashCode());
    }

}