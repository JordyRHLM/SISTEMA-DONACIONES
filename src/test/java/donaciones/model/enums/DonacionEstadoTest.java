package donaciones.model.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DonacionEstadoTest {

    @Test
    void testPendiente() {
        assertEquals("PENDIENTE", DonacionEstado.PENDIENTE.toString());
    }

    @Test
    void testConfirmada() {
        assertEquals("CONFIRMADA", DonacionEstado.CONFIRMADA.toString());
    }

    @Test
    void testEntregada() {
        assertEquals("ENTREGADA", DonacionEstado.ENTREGADA.toString());
    }

    @Test
    void testValuesLength() {
        assertEquals(3, DonacionEstado.values().length);
    }

    @Test
    void testValueOf() {
        assertEquals(DonacionEstado.CONFIRMADA, DonacionEstado.valueOf("CONFIRMADA"));
    }
}