package donaciones.model.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TipoDonacionTest {

    @Test
    void testDinero() {
        assertEquals("DINERO", TipoDonacion.DINERO.toString());
    }

    @Test
    void testAlimentos() {
        assertEquals("ALIMENTOS", TipoDonacion.ALIMENTOS.toString());
    }

    @Test
    void testRopa() {
        assertEquals("ROPA", TipoDonacion.ROPA.toString());
    }

    @Test
    void testMedicinas() {
        assertEquals("MEDICINAS", TipoDonacion.MEDICINAS.toString());
    }

    @Test
    void testOtros() {
        assertEquals("OTROS", TipoDonacion.OTROS.toString());
    }
}