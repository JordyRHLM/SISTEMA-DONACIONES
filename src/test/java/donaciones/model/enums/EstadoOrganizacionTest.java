package donaciones.model.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EstadoOrganizacionTest {

    @Test
    void testPendiente() {
        assertEquals("PENDIENTE", EstadoOrganizacion.PENDIENTE.toString());
    }

    @Test
    void testAprobada() {
        assertEquals("APROBADA", EstadoOrganizacion.APROBADA.toString());
    }

    @Test
    void testRechazada() {
        assertEquals("RECHAZADA", EstadoOrganizacion.RECHAZADA.toString());
    }

    @Test
    void testValuesLength() {
        assertEquals(3, EstadoOrganizacion.values().length);
    }

    @Test
    void testValueOf() {
        assertEquals(EstadoOrganizacion.APROBADA, EstadoOrganizacion.valueOf("APROBADA"));
    }
}