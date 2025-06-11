package donaciones.model.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CampaniaEstadoTest {

    @Test
    void testGetNombre() {
        assertEquals("Borrador", CampaniaEstado.BORRADOR.getNombre());
    }

    @Test
    void testFromString() {
        assertEquals(CampaniaEstado.ACTIVA, CampaniaEstado.fromString("Activa"));
        assertEquals(CampaniaEstado.ACTIVA, CampaniaEstado.fromString("ACTIVA"));
    }

    @Test
    void testIsEditable() {
        assertTrue(CampaniaEstado.BORRADOR.isEditable());
        assertFalse(CampaniaEstado.ACTIVA.isEditable());
    }

    @Test
    void testIsActive() {
        assertTrue(CampaniaEstado.ACTIVA.isActive());
        assertFalse(CampaniaEstado.BORRADOR.isActive());
    }

    @Test
    void testIsFinalState() {
        assertTrue(CampaniaEstado.COMPLETADA.isFinalState());
        assertFalse(CampaniaEstado.ACTIVA.isFinalState());
    }
}