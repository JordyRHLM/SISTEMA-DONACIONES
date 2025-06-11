package donaciones.model.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RolColaboracionTest {

    @Test
    void testVoluntarioExists() {
        assertNotNull(RolColaboracion.VOLUNTARIO);
    }

    @Test
    void testPromotorExists() {
        assertNotNull(RolColaboracion.PROMOTOR);
    }

    @Test
    void testAyudanteExists() {
        assertNotNull(RolColaboracion.AYUDANTE);
    }

    @Test
    void testEnumValuesLength() {
        assertEquals(3, RolColaboracion.values().length);
    }

    @Test
    void testEnumValuesOrder() {
        assertEquals(RolColaboracion.VOLUNTARIO, RolColaboracion.values()[0]);
        assertEquals(RolColaboracion.PROMOTOR, RolColaboracion.values()[1]);
        assertEquals(RolColaboracion.AYUDANTE, RolColaboracion.values()[2]);
    }
}