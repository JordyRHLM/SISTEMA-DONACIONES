package donaciones.repository;

import donaciones.model.Campania;
import donaciones.model.Organizacion;
import donaciones.model.Usuario;
import donaciones.model.enums.CampaniaEstado;
import donaciones.model.enums.EstadoOrganizacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // si usas DB real
public class CampaniaRepositoryTest {

        @Autowired
        private ColaboracionRepository colaboracionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CampaniaRepository campaniaRepository;

    @Autowired
    private OrganizacionRepository organizacionRepository;

    @Test
    void testFindByOrganizacionIdAndEstado() {
        Usuario userTest = new Usuario();
        userTest.setEmail("usuario.test@example.com");
        userTest.setNombre("Usuario Test");
        userTest.setIsAdmin(false);
        userTest.setIsOrgOwner(false);
        userTest.setPassword("123456"); // <- Asignar password aquí

        usuarioRepository.save(userTest);


        Organizacion org = new Organizacion();
        org.setNombre("Org Test");
        org.setOwner(userTest);
        organizacionRepository.save(org);

        Campania campania = Campania.builder()
                .titulo("Campaña Test")
                .descripcion("Descripción")
                .organizacion(org)
                .fechaInicio(LocalDate.now())
                .fechaFin(LocalDate.now().plusDays(30))
                .metaMonetaria(BigDecimal.valueOf(1000))
                .estado(CampaniaEstado.ACTIVA)
                .build();
        campania.setMetaItemsMap(Map.of("item1", 10));
        campaniaRepository.save(campania);

        List<Campania> porOrg = campaniaRepository.findByOrganizacionId(org.getId());
        assertThat(porOrg).isNotEmpty();

        List<Campania> porEstado = campaniaRepository.findByEstado(CampaniaEstado.ACTIVA);
        assertThat(porEstado).isNotEmpty();
    }




    @Test
void countDistinctOrganizacionByEstado_returns_count_for_given_estado() {

    // Limpiar las tablas en orden correcto para evitar violación de FK
    colaboracionRepository.deleteAll();
    campaniaRepository.deleteAll();
    organizacionRepository.deleteAll();
    usuarioRepository.deleteAll();

    // Crear usuarios
    Usuario user1 = new Usuario();
    user1.setEmail("user1@example.com");
    user1.setNombre("User 1");
    user1.setPassword("123456");
    usuarioRepository.save(user1);

    Usuario user2 = new Usuario();
    user2.setEmail("user2@example.com");
    user2.setNombre("User 2");
    user2.setPassword("123456");
    usuarioRepository.save(user2);

    Usuario user3 = new Usuario();
    user3.setEmail("user3@example.com");
    user3.setNombre("User 3");
    user3.setPassword("123456");
    usuarioRepository.save(user3);

    // Crear organizaciones asociadas a los usuarios
    Organizacion org1 = new Organizacion();
    org1.setNombre("Org 1");
    org1.setOwner(user1);
    organizacionRepository.save(org1);

    Organizacion org2 = new Organizacion();
    org2.setNombre("Org 2");
    org2.setOwner(user2);
    organizacionRepository.save(org2);

    Organizacion org3 = new Organizacion();
    org3.setNombre("Org 3");
    org3.setOwner(user3);
    organizacionRepository.save(org3);

    // Crear campañas vinculadas a las organizaciones
    Campania campania1 = Campania.builder()
            .estado(CampaniaEstado.ACTIVA)
            .organizacion(org1)
            .titulo("Campania 1")
            .metaMonetaria(BigDecimal.valueOf(100))
            .fechaInicio(LocalDate.now())
            .fechaFin(LocalDate.now().plusDays(10))
            .build();

    Campania campania2 = Campania.builder()
            .estado(CampaniaEstado.ACTIVA)
            .organizacion(org1)
            .titulo("Campania 2")
            .metaMonetaria(BigDecimal.valueOf(200))
            .fechaInicio(LocalDate.now())
            .fechaFin(LocalDate.now().plusDays(10))
            .build();

    Campania campania3 = Campania.builder()
            .estado(CampaniaEstado.ACTIVA)
            .organizacion(org2)
            .titulo("Campania 3")
            .metaMonetaria(BigDecimal.valueOf(300))
            .fechaInicio(LocalDate.now())
            .fechaFin(LocalDate.now().plusDays(10))
            .build();

    Campania campania4 = Campania.builder()
            .estado(CampaniaEstado.COMPLETADA)
            .organizacion(org3)
            .titulo("Campania 4")
            .metaMonetaria(BigDecimal.valueOf(400))
            .fechaInicio(LocalDate.now().minusDays(30))
            .fechaFin(LocalDate.now().minusDays(10))
            .build();

    campaniaRepository.save(campania1);
    campaniaRepository.save(campania2);
    campaniaRepository.save(campania3);
    campaniaRepository.save(campania4);

    // Ejecutar el conteo de organizaciones distintas con campañas activas
    long count = campaniaRepository.countDistinctOrganizacionByEstado(CampaniaEstado.ACTIVA);

    // Verificar que el resultado es el esperado
    assertThat(count).isEqualTo(3);
}



}


