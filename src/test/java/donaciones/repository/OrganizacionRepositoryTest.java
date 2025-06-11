package donaciones.repository;

import donaciones.model.Organizacion;
import donaciones.model.Usuario;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrganizacionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrganizacionRepository organizacionRepository;

    @Test
    void testExistsByNombre_ExistingName() {
        // Crear y persistir el owner (usuario) usando los setters reales
        Usuario owner = new Usuario();
        owner.setEmail("test@example.com");
        owner.setNombre("Usuario de Prueba");
        owner.setPassword("password123"); // Usa un valor válido según tu lógica
        owner.setIsAdmin(false);
        owner.setIsOrgOwner(true); // Asumimos que este usuario puede ser owner de una organización

        entityManager.persist(owner);
        entityManager.flush();

        // Crear y persistir la organización con el owner asignado
        Organizacion organizacion = new Organizacion();
        organizacion.setNombre("Test Org");
        organizacion.setOwner(owner); // Asignar el owner obligatorio

        entityManager.persist(organizacion);
        entityManager.flush();

        // Ejecutar la prueba
        boolean exists = organizacionRepository.existsByNombre("Test Org");
        assertThat(exists).isTrue();
    }


    @Test
    void testExistsByNombre_NonExistingName() {
        boolean exists = organizacionRepository.existsByNombre("Non Existing Org");
        assertThat(exists).isFalse();
    }

    @Test
    void testFindByNombre_ExistingName() {
        // Crear y persistir un usuario owner válido
        Usuario owner = new Usuario();
        owner.setEmail("owner@example.com");
        owner.setNombre("Owner Usuario");
        owner.setPassword("password123");
        owner.setIsAdmin(false);
        owner.setIsOrgOwner(true);

        entityManager.persist(owner);
        entityManager.flush();

        // Crear y persistir la organización con owner asignado
        Organizacion organizacion = new Organizacion();
        organizacion.setNombre("Find Me");
        organizacion.setOwner(owner); // obligatorio para evitar error

        entityManager.persist(organizacion);
        entityManager.flush();

        // Buscar la organización por nombre y verificar
        Optional<Organizacion> found = organizacionRepository.findByNombre("Find Me");
        assertThat(found).isPresent();
        assertThat(found.get().getNombre()).isEqualTo("Find Me");
    }


    @Test
    void testFindByNombre_NonExistingName() {
        Optional<Organizacion> found = organizacionRepository.findByNombre("Not Here");
        assertThat(found).isEmpty();
    }

    @Test
    void testSaveOrganizacion() {
        // Crear y persistir usuario owner válido
        Usuario owner = new Usuario();
        owner.setEmail("owner@example.com");
        owner.setNombre("Owner User");
        owner.setPassword("password123");
        owner.setIsAdmin(false);
        owner.setIsOrgOwner(true);

        entityManager.persist(owner);
        entityManager.flush();

        // Crear organización y asignar owner
        Organizacion organizacion = new Organizacion();
        organizacion.setNombre("New Org");
        organizacion.setOwner(owner);  // Asignar owner obligatorio

        Organizacion savedOrganizacion = organizacionRepository.save(organizacion);

        assertThat(savedOrganizacion.getId()).isNotNull();
        assertThat(savedOrganizacion.getNombre()).isEqualTo("New Org");
        assertThat(savedOrganizacion.getOwner()).isEqualTo(owner);
    }

}