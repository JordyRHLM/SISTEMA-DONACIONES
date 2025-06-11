package donaciones.dto.response;

import donaciones.model.enums.CampaniaEstado;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CampaniaResponseTest {

    @Test
    void testNoArgsConstructor() {
        CampaniaResponse response = new CampaniaResponse();
        assertNotNull(response);
    }

    @Test
    void testAllArgsConstructor() {
        CampaniaResponse response = new CampaniaResponse(1L, "Test", "Desc", 1L, "Org", LocalDate.now(), LocalDate.now(), BigDecimal.TEN, "{}", CampaniaEstado.ACTIVA, LocalDateTime.now());
        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void testBuilder() {
        CampaniaResponse response = CampaniaResponse.builder().id(1L).titulo("Test").build();
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Test", response.getTitulo());
    }

    @Test
    void testGettersAndSetters() {
        CampaniaResponse response = new CampaniaResponse();
        response.setId(1L);
        assertEquals(1L, response.getId());
    }

    @Test
    void testEqualsAndHashCode() {
        CampaniaResponse response1 = CampaniaResponse.builder().id(1L).titulo("Test").build();
        assertEquals(response1, response1);
        assertEquals(response1.hashCode(), response1.hashCode());
    }
}