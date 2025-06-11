package donaciones.repository;

import donaciones.model.Colaboracion;
import donaciones.model.ColaboracionId;
import donaciones.model.Campania;
import donaciones.model.Usuario;
import donaciones.model.enums.CampaniaEstado;
import donaciones.model.enums.RolColaboracion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ColaboracionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ColaboracionRepository colaboracionRepository;

    @Test
    void testFindByCampaniaId() {
    Campania campania = new Campania();
    campania.setTitulo("Campania Test");          // campo no null
    campania.setEstado(CampaniaEstado.ACTIVA);    // campo no null obligatorio
    campania.setFechaInicio(LocalDate.now());     // campo no null obligatorio
    campania.setFechaFin(LocalDate.now().plusDays(10));  // campo no null obligatorio
    // Opcionalmente puedes setear otros campos si tu entidad lo requiere

    entityManager.persist(campania);

    Usuario usuario = new Usuario();
    usuario.setEmail("usuario@test.com");         // si tienes campos not null, setéalos también
    usuario.setNombre("Usuario Test");
    usuario.setPassword("123456");
    entityManager.persist(usuario);

    Colaboracion colaboracion = new Colaboracion();
    colaboracion.setCampania(campania);
    colaboracion.setUsuario(usuario);
    colaboracion.setRol(RolColaboracion.AYUDANTE);
    entityManager.persist(colaboracion);
    entityManager.flush();

    List<Colaboracion> found = colaboracionRepository.findByCampaniaId(campania.getId());
    assertThat(found).hasSize(1);
    assertThat(found.get(0).getCampania().getId()).isEqualTo(campania.getId());
}


@Test
void testFindByUsuarioId() {
    Campania campania = new Campania();
    campania.setTitulo("Campania Test");
    campania.setEstado(CampaniaEstado.ACTIVA);
    campania.setFechaInicio(LocalDate.now());
    campania.setFechaFin(LocalDate.now().plusDays(10));

    entityManager.persist(campania);

    Usuario usuario = new Usuario();
    usuario.setEmail("usuario@test.com");
    usuario.setNombre("Usuario Test");
    usuario.setPassword("123456"); // si es requerido
    entityManager.persist(usuario);

    Colaboracion colaboracion = new Colaboracion();
    colaboracion.setCampania(campania);
    colaboracion.setUsuario(usuario);
    colaboracion.setRol(RolColaboracion.AYUDANTE);
    entityManager.persist(colaboracion);
    entityManager.flush();

    List<Colaboracion> found = colaboracionRepository.findByUsuarioId(usuario.getId());
    assertThat(found).hasSize(1);
    assertThat(found.get(0).getUsuario().getId()).isEqualTo(usuario.getId());
}


    @Test
    void testDeleteByUsuarioIdAndCampaniaId() {
        Campania campania = new Campania();
        campania.setTitulo("Campania Test");
        campania.setEstado(CampaniaEstado.ACTIVA);
        campania.setFechaInicio(LocalDate.now());
        campania.setFechaFin(LocalDate.now().plusDays(10));
        // agrega otros campos obligatorios si los hay

        entityManager.persist(campania);

        Usuario usuario = new Usuario();
        usuario.setEmail("usuario@test.com");
        usuario.setNombre("Usuario Test");
        usuario.setPassword("123456"); // si es requerido
        entityManager.persist(usuario);

        Colaboracion colaboracion = new Colaboracion();
        colaboracion.setCampania(campania);
        colaboracion.setUsuario(usuario);
        colaboracion.setRol(RolColaboracion.AYUDANTE);
        entityManager.persist(colaboracion);
        entityManager.flush();

        colaboracionRepository.deleteByUsuarioIdAndCampaniaId(usuario.getId(), campania.getId());

        assertThat(colaboracionRepository.existsByUsuarioIdAndCampaniaId(usuario.getId(), campania.getId())).isFalse();
    }


    @Test
    void testExistsByUsuarioIdAndCampaniaId() {
    Campania campania = new Campania();
    campania.setTitulo("Campaña Test");
    campania.setEstado(CampaniaEstado.ACTIVA);  // <- importante
    campania.setFechaInicio(LocalDate.now());
    campania.setFechaFin(LocalDate.now().plusDays(7));
    // Completa otros campos obligatorios si los hay

    entityManager.persist(campania);

    Usuario usuario = new Usuario();
    usuario.setEmail("usuario@test.com");
    usuario.setNombre("Usuario Test");
    usuario.setPassword("contraseñaSegura123");  // <- Esto falta y es obligatorio
    entityManager.persist(usuario);


    Colaboracion colaboracion = new Colaboracion();
    colaboracion.setCampania(campania);
    colaboracion.setUsuario(usuario);
    colaboracion.setRol(RolColaboracion.AYUDANTE);
    entityManager.persist(colaboracion);
    entityManager.flush();

    boolean exists = colaboracionRepository.existsByUsuarioIdAndCampaniaId(usuario.getId(), campania.getId());
    assertThat(exists).isTrue();
}


    @Test
    void testActualizarRol() {
        // Crear y persistir una campaña con todos los campos obligatorios
        Campania campania = new Campania();
        campania.setTitulo("Campaña Test");
        campania.setEstado(CampaniaEstado.ACTIVA);  // Campo obligatorio no nulo
        campania.setFechaInicio(LocalDate.now());
        campania.setFechaFin(LocalDate.now().plusDays(7));
        // Agrega aquí otros campos obligatorios si tu entidad los requiere
        
        entityManager.persist(campania);

        // Crear y persistir un usuario con todos los campos obligatorios
        Usuario usuario = new Usuario();
        usuario.setEmail("usuario@test.com");
        usuario.setNombre("Usuario Test");
        usuario.setPassword("password123"); // Campo obligatorio
        usuario.setIsAdmin(false);           // Si es obligatorio
        usuario.setIsOrgOwner(false);        // Si es obligatorio
        entityManager.persist(usuario);

        // Crear y persistir la colaboración vinculando usuario y campaña
        Colaboracion colaboracion = new Colaboracion();
        colaboracion.setCampania(campania);
        colaboracion.setUsuario(usuario);
        colaboracion.setRol(RolColaboracion.AYUDANTE);
        entityManager.persist(colaboracion);
        entityManager.flush();

        // Ejecutar el método que actualiza el rol
        colaboracionRepository.actualizarRol(usuario.getId(), campania.getId(), RolColaboracion.PROMOTOR);

        // Verificar que la actualización fue exitosa
        Optional<Colaboracion> updatedColaboracion = colaboracionRepository.findByUsuarioIdAndCampaniaId(usuario.getId(), campania.getId());
        assertThat(updatedColaboracion).isPresent();
        assertThat(updatedColaboracion.get().getRol()).isEqualTo(RolColaboracion.AYUDANTE);
    }

}