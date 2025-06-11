package donaciones.dto.response;

import donaciones.model.enums.DonacionEstado;
import donaciones.model.enums.TipoDonacion;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DonacionResponseTest {

    @Test
    void testGettersAndSetters() {
        DonacionResponse response = new DonacionResponse();
        response.setId(1L);
        response.setTipo(TipoDonacion.ALIMENTOS);
        response.setMonto(100.0);
        response.setItems("Arroz");
        response.setUsuarioId(2L);
        response.setCampaniaId(3L);
        response.setOrganizacionId(4L);
        response.setEstado(DonacionEstado.PENDIENTE);
        response.setIsAnonima(true);
        response.setCreatedAt(LocalDateTime.now());

        assertNotNull(response.getId());
        assertNotNull(response.getTipo());
        assertNotNull(response.getMonto());
        assertNotNull(response.getItems());
        assertNotNull(response.getUsuarioId());
        assertNotNull(response.getCampaniaId());
        assertNotNull(response.getOrganizacionId());
        assertNotNull(response.getEstado());
        assertNotNull(response.getIsAnonima());
        assertNotNull(response.getCreatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        DonacionResponse response1 = new DonacionResponse();
        response1.setId(1L);
        DonacionResponse response2 = new DonacionResponse();
        response2.setId(1L);

        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void testNotEquals() {
        DonacionResponse response1 = new DonacionResponse();
        response1.setId(1L);
        DonacionResponse response2 = new DonacionResponse();
        response2.setId(2L);

        assertNotEquals(response1, response2);
    }

    @Test
    void testToString() {
        DonacionResponse response = new DonacionResponse();
        response.setId(1L);
        assertNotNull(response.toString());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        DonacionResponse response = new DonacionResponse();
        response.setId(1L);
        response.setTipo(TipoDonacion.ALIMENTOS);
        response.setMonto(100.0);
        response.setItems("Arroz");
        response.setUsuarioId(2L);
        response.setCampaniaId(3L);
        response.setOrganizacionId(4L);
        response.setEstado(DonacionEstado.PENDIENTE);
        response.setIsAnonima(true);
        response.setCreatedAt(now);

        assertEquals(1L, response.getId());
        assertEquals(TipoDonacion.ALIMENTOS, response.getTipo());
        assertEquals(100.0, response.getMonto());
        assertEquals("Arroz", response.getItems());
        assertEquals(2L, response.getUsuarioId());
        assertEquals(3L, response.getCampaniaId());
        assertEquals(4L, response.getOrganizacionId());
        assertEquals(DonacionEstado.PENDIENTE, response.getEstado());
        assertTrue(response.getIsAnonima());
        assertEquals(now, response.getCreatedAt());
    }
}