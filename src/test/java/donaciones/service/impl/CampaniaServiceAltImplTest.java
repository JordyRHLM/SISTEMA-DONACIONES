package donaciones.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import donaciones.dto.request.CampaniaRequest;
import donaciones.dto.response.CampaniaRecaudacionResponse;
import donaciones.model.Campania;
import donaciones.model.Organizacion;
import donaciones.model.enums.DonacionEstado;
import donaciones.repository.CampaniaRepository;
import donaciones.repository.DonacionRepository;
import donaciones.repository.OrganizacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CampaniaServiceAltImplTest {

    @Mock
    private CampaniaRepository campaniaRepository;
    @Mock
    private OrganizacionRepository organizacionRepository;
    @Mock
    private DonacionRepository donacionRepository;
    @Mock
    private ObjectMapper objectMapper;
    @InjectMocks
    private CampaniaServiceAltImpl campaniaService;

    private CampaniaRequest campaniaRequest;
    private Campania campania;
    private Organizacion organizacion;

    @BeforeEach
    void setUp() {
        campaniaRequest = new CampaniaRequest();
        campaniaRequest.setTitulo("Test Campania");
        campaniaRequest.setDescripcion("Test Description");
        campaniaRequest.setOrganizacionId(1L);
        campaniaRequest.setFechaInicio(LocalDate.now());
        campaniaRequest.setFechaFin(LocalDate.now().plusDays(7));
        campaniaRequest.setMetaMonetaria(BigDecimal.valueOf(1000));
        campaniaRequest.setMetaItems(Map.of("item1", 10));

        organizacion = new Organizacion();
        organizacion.setId(1L);
        organizacion.setNombre("Test Org");

        campania = Campania.builder()
                .id(1L)
                .titulo("Test Campania")
                .descripcion("Test Description")
                .organizacion(organizacion)
                .fechaInicio(LocalDate.now())
                .fechaFin(LocalDate.now().plusDays(7))
                .metaMonetaria(BigDecimal.valueOf(1000))
                .build();
    }

    @Test
    void crearCampania_success() {
        when(organizacionRepository.findById(1L)).thenReturn(Optional.of(organizacion));
        when(campaniaRepository.save(any(Campania.class))).thenReturn(campania);

        assertNotNull(campaniaService.crearCampania(campaniaRequest));
    }

    @Test
    void obtenerPorId_success() {
        when(campaniaRepository.findById(1L)).thenReturn(Optional.of(campania));

        assertNotNull(campaniaService.obtenerPorId(1L));
    }

    @Test
    void obtenerTodas_success() {
        when(campaniaRepository.findAll()).thenReturn(List.of(campania));

        assertFalse(campaniaService.obtenerTodas().isEmpty());
    }

    @Test
    void calcularRecaudacion_success() {
        // Configuración
        campania.setMetaMonetaria(BigDecimal.valueOf(100));
        when(campaniaRepository.findById(1L)).thenReturn(Optional.of(campania));
        when(donacionRepository.findByCampaniaIdAndEstadoIn(1L, 
            List.of(DonacionEstado.CONFIRMADA, DonacionEstado.ENTREGADA)))
            .thenReturn(List.of());

        // Ejecución
        CampaniaRecaudacionResponse recaudacion = campaniaService.calcularRecaudacion(1L);

        // Verificación (elige una opción)
        assertEquals(0, BigDecimal.ZERO.compareTo(recaudacion.getTotalRecaudado())); // Opción 1
        // O
        assertEquals(BigDecimal.ZERO.setScale(2), recaudacion.getTotalRecaudado().setScale(2)); // Opción 3
    }
}