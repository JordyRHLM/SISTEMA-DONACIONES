package donaciones.model;

import donaciones.model.enums.EstadoOrganizacion;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrganizacionTest {

    @Test
    void testOrganizacionCreation() {
        Organizacion organizacion = new Organizacion();
        assertNotNull(organizacion);
    }

    @Test
    void testOrganizacionBuilder() {
        Organizacion organizacion = Organizacion.builder()
                .nombre("Test Org")
                .descripcion("Test Description")
                .estado(EstadoOrganizacion.APROBADA)
                .build();

        assertEquals("Test Org", organizacion.getNombre());
        assertEquals("Test Description", organizacion.getDescripcion());
        assertEquals(EstadoOrganizacion.APROBADA, organizacion.getEstado());
    }

    @Test
    void testCreatedAtIsSetOnCreation() {
        Organizacion organizacion = new Organizacion();
        assertNotNull(organizacion.getCreatedAt());
    }

    @Test
    void testSetAndGetNombre() {
        Organizacion organizacion = new Organizacion();
        organizacion.setNombre("New Name");
        assertEquals("New Name", organizacion.getNombre());
    }

    @Test
    void testDefaultEstadoIsPendiente() {
        Organizacion organizacion = new Organizacion();
        assertEquals(EstadoOrganizacion.PENDIENTE, organizacion.getEstado());
    }
}