package donaciones.dto.response;

import donaciones.model.enums.RolColaboracion;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ColaboracionResponseTest {

    @Test
    void testColaboracionResponseGettersAndSetters() {
        ColaboracionResponse response = new ColaboracionResponse();
        response.setUsuarioId(1L);
        response.setUsuarioNombre("Test User");
        response.setCampaniaId(2L);
        response.setCampaniaTitulo("Test Campaign");
        response.setRol(RolColaboracion.VOLUNTARIO);
        response.setFechaColaboracion(LocalDateTime.now());

        assertEquals(1L, response.getUsuarioId());
        assertEquals("Test User", response.getUsuarioNombre());
        assertEquals(2L, response.getCampaniaId());
        assertEquals("Test Campaign", response.getCampaniaTitulo());
        assertEquals(RolColaboracion.VOLUNTARIO, response.getRol());
        assertNotNull(response.getFechaColaboracion());
    }

    @Test
    void testColaboracionResponseEquals() {
        ColaboracionResponse response1 = new ColaboracionResponse();
        response1.setUsuarioId(1L);
        response1.setCampaniaId(2L);

        ColaboracionResponse response2 = new ColaboracionResponse();
        response2.setUsuarioId(1L);
        response2.setCampaniaId(2L);

        assertEquals(response1, response2);
    }

    @Test
    void testColaboracionResponseNotEquals() {
        ColaboracionResponse response1 = new ColaboracionResponse();
        response1.setUsuarioId(1L);
        response1.setCampaniaId(2L);

        ColaboracionResponse response2 = new ColaboracionResponse();
        response2.setUsuarioId(3L);
        response2.setCampaniaId(4L);

        assertNotEquals(response1, response2);
    }

    @Test
    void testColaboracionResponseHashCode() {
        ColaboracionResponse response1 = new ColaboracionResponse();
        response1.setUsuarioId(1L);
        response1.setCampaniaId(2L);

        ColaboracionResponse response2 = new ColaboracionResponse();
        response2.setUsuarioId(1L);
        response2.setCampaniaId(2L);

        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void testColaboracionResponseToString() {
        ColaboracionResponse response = new ColaboracionResponse();
        response.setUsuarioId(1L);
        response.setUsuarioNombre("Test User");
        response.setCampaniaId(2L);
        response.setCampaniaTitulo("Test Campaign");
        response.setRol(RolColaboracion.VOLUNTARIO);
        response.setFechaColaboracion(LocalDateTime.now());

        assertNotNull(response.toString());
    }
}