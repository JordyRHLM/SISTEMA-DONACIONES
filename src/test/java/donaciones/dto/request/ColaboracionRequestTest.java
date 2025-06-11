package donaciones.dto.request;

import donaciones.model.enums.RolColaboracion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColaboracionRequestTest {

    @Test
    void testSetAndGetUsuarioId() {
        ColaboracionRequest request = new ColaboracionRequest();
        request.setUsuarioId(123L);
        assertEquals(123L, request.getUsuarioId());
    }

    @Test
    void testSetAndGetCampaniaId() {
        ColaboracionRequest request = new ColaboracionRequest();
        request.setCampaniaId(456L);
        assertEquals(456L, request.getCampaniaId());
    }

    @Test
    void testSetAndGetRol() {
        ColaboracionRequest request = new ColaboracionRequest();
        request.setRol(RolColaboracion.PROMOTOR);
        assertEquals(RolColaboracion.PROMOTOR, request.getRol());
    }

    @Test
    void testDefaultValues() {
        ColaboracionRequest request = new ColaboracionRequest();
        assertNull(request.getUsuarioId());
        assertNull(request.getCampaniaId());
        assertNull(request.getRol());
    }

    @Test
    void testAllFieldsSet() {
        ColaboracionRequest request = new ColaboracionRequest();
        request.setUsuarioId(1L);
        request.setCampaniaId(2L);
        request.setRol(RolColaboracion.VOLUNTARIO);

        assertEquals(1L, request.getUsuarioId());
        assertEquals(2L, request.getCampaniaId());
        assertEquals(RolColaboracion.VOLUNTARIO, request.getRol());
    }
}