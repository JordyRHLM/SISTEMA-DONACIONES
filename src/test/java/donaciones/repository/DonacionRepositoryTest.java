package donaciones.repository;

import donaciones.model.Campania;
import donaciones.model.Donacion;
import donaciones.model.Organizacion;
import donaciones.model.Usuario;
import donaciones.model.enums.DonacionEstado;
import donaciones.model.enums.TipoDonacion;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DonacionRepositoryTest {

    @Autowired
    private DonacionRepository donacionRepository;

    @Test
    void findByUsuarioId_ReturnsDonations() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        Donacion donacion = new Donacion();
        donacion.setUsuario(usuario);
        donacion.setMonto(100.0);
        donacion.setTipo(TipoDonacion.DINERO);

        donacionRepository.save(donacion);

        List<Donacion> donaciones = donacionRepository.findByUsuarioId(1L);

        assertThat(donaciones).isNotNull();
    }

    @Test
    void findByCampaniaIdAndEstadoIn_ReturnsDonations() {
        Donacion donacion = new Donacion();

        Campania campania = new Campania();
        campania.setId(1L); // simulamos una campaña con id 1
        donacion.setCampania(campania);

        donacion.setEstado(DonacionEstado.PENDIENTE);

        donacionRepository.save(donacion);

        List<DonacionEstado> estados = Arrays.asList(DonacionEstado.PENDIENTE);
        List<Donacion> donaciones = donacionRepository.findByCampaniaIdAndEstadoIn(1L, estados);

        assertThat(donaciones).isNotEmpty();
    }

    @Test
    void sumMontoByEstadoIn_ReturnsSum() {
        Donacion donacion1 = new Donacion();
        donacion1.setMonto(100.0);
        donacion1.setEstado(DonacionEstado.CONFIRMADA);
        donacionRepository.save(donacion1);

        Donacion donacion2 = new Donacion();
        donacion2.setMonto(50.0);
        donacion2.setEstado(DonacionEstado.CONFIRMADA);
        donacionRepository.save(donacion2);

        List<DonacionEstado> estados = Arrays.asList(DonacionEstado.CONFIRMADA);
        BigDecimal sum = donacionRepository.sumMontoByEstadoIn(estados);
        assertThat(sum).isEqualTo(BigDecimal.valueOf(150.0));
    }

    @Test
void findByOrganizacionIdAndEstado_ReturnsDonations() {
    Donacion donacion = new Donacion();

    Organizacion organizacion = new Organizacion();
    organizacion.setId(1L);
    donacion.setOrganizacion(organizacion);

    donacion.setEstado(DonacionEstado.CONFIRMADA); // Estado válido

    donacionRepository.save(donacion);

    List<Donacion> donaciones = donacionRepository.findByOrganizacionIdAndEstado(1L, DonacionEstado.CONFIRMADA);

    assertThat(donaciones).isNotEmpty();
}


    @Test
    void findByIdAndOrganizacionId_ReturnsDonation() {
        Donacion donacion = new Donacion();

        Organizacion organizacion = new Organizacion();
        organizacion.setId(1L); // solo necesitas asignar el ID
        donacion.setOrganizacion(organizacion); // asignar el objeto completo

        donacion = donacionRepository.save(donacion);

        Donacion foundDonation = donacionRepository.findByIdAndOrganizacionId(donacion.getId(), 1L);
        assertThat(foundDonation).isNotNull();
    }

}