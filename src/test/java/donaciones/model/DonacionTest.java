package donaciones.model;

import donaciones.model.enums.DonacionEstado;
import donaciones.model.enums.TipoDonacion;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DonacionTest {

    @Test
    void testDonacionCreation() {
        Donacion donacion = new Donacion();
        assertNotNull(donacion);
        assertEquals(DonacionEstado.PENDIENTE, donacion.getEstado());
        assertFalse(donacion.getIsAnonima());
        assertNotNull(donacion.getCreatedAt());
    }

    @Test
    void testSettersAndGetters() {
        Donacion donacion = new Donacion();
        donacion.setId(1L);
        donacion.setTipo(TipoDonacion.DINERO);
        donacion.setMonto(100.0);
        donacion.setItems("{\"item\": \"value\"}");
        donacion.setEstado(DonacionEstado.CONFIRMADA);
        donacion.setIsAnonima(true);
        LocalDateTime now = LocalDateTime.now();
        donacion.setCreatedAt(now);

        assertEquals(1L, donacion.getId());
        assertEquals(TipoDonacion.DINERO, donacion.getTipo());
        assertEquals(100.0, donacion.getMonto());
        assertEquals("{\"item\": \"value\"}", donacion.getItems());
        assertEquals(DonacionEstado.CONFIRMADA, donacion.getEstado());
        assertTrue(donacion.getIsAnonima());
        assertEquals(now, donacion.getCreatedAt());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Donacion donacion = new Donacion(1L, TipoDonacion.DINERO, 100.0, "{\"item\": \"value\"}", null, null, null, DonacionEstado.CONFIRMADA, true, now);

        assertEquals(1L, donacion.getId());
        assertEquals(TipoDonacion.DINERO, donacion.getTipo());
        assertEquals(100.0, donacion.getMonto());
        assertEquals("{\"item\": \"value\"}", donacion.getItems());
        assertEquals(DonacionEstado.CONFIRMADA, donacion.getEstado());
        assertTrue(donacion.getIsAnonima());
        assertEquals(now, donacion.getCreatedAt());
    }

    @Test
    void testDefaultEstadoIsPendiente() {
        Donacion donacion = new Donacion();
        assertEquals(DonacionEstado.PENDIENTE, donacion.getEstado());
    }

    @Test
    void testDefaultIsAnonimaIsFalse() {
        Donacion donacion = new Donacion();
        assertFalse(donacion.getIsAnonima());
    }
}