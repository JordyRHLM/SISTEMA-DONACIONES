package donaciones.model;

import donaciones.model.enums.RolColaboracion;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ColaboracionTest {

    @Test
    void testColaboracionCreation() {
        Colaboracion colaboracion = new Colaboracion();
        assertNotNull(colaboracion);
    }

    @Test
    void testSetAndGetUsuario() {
        Colaboracion colaboracion = new Colaboracion();
        Usuario usuario = new Usuario();
        colaboracion.setUsuario(usuario);
        assertEquals(usuario, colaboracion.getUsuario());
    }

    @Test
    void testSetAndGetCampania() {
        Colaboracion colaboracion = new Colaboracion();
        Campania campania = new Campania();
        colaboracion.setCampania(campania);
        assertEquals(campania, colaboracion.getCampania());
    }

    @Test
    void testSetAndGetRol() {
        Colaboracion colaboracion = new Colaboracion();
        colaboracion.setRol(RolColaboracion.VOLUNTARIO);
        assertEquals(RolColaboracion.VOLUNTARIO, colaboracion.getRol());
    }

    @Test
    void testCreatedAtIsNotNull() {
        Colaboracion colaboracion = new Colaboracion();
        colaboracion.setCreatedAt(LocalDateTime.now());
        assertNotNull(colaboracion.getCreatedAt());
    }
}