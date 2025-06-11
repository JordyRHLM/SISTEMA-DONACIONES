package donaciones.service.impl;

import donaciones.dto.request.DonacionRequest;
import donaciones.dto.response.DonacionResponse;
import donaciones.exception.RecursoNoEncontradoException;
import donaciones.model.*;
import donaciones.model.enums.DonacionEstado;
import donaciones.model.enums.TipoDonacion;
import donaciones.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DonacionServiceImplTest {

    @Mock
    private DonacionRepository donacionRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private CampaniaRepository campaniaRepository;
    @Mock
    private OrganizacionRepository organizacionRepository;
    @InjectMocks
    private DonacionServiceImpl donacionService;

    @Test
    void crearDonacion_Success() {
        DonacionRequest request = new DonacionRequest();
        request.setUsuarioId(1L);
        request.setCampaniaId(1L);
        request.setOrganizacionId(1L);
        request.setTipo(TipoDonacion.DINERO);
        request.setMonto(100.0);

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        Campania campania = new Campania();
        campania.setId(1L);
        Organizacion organizacion = new Organizacion();
        organizacion.setId(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(campaniaRepository.findById(1L)).thenReturn(Optional.of(campania));
        when(organizacionRepository.findById(1L)).thenReturn(Optional.of(organizacion));

        Donacion donacion = new Donacion();
        donacion.setId(1L);
        donacion.setTipo(TipoDonacion.DINERO);
        donacion.setMonto(100.0);
        donacion.setUsuario(usuario);
        donacion.setCampania(campania);
        donacion.setOrganizacion(organizacion);
        donacion.setEstado(DonacionEstado.PENDIENTE);

        when(donacionRepository.save(any(Donacion.class))).thenReturn(donacion);

        DonacionResponse response = donacionService.crearDonacion(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("DINERO", response.getTipo().name());
        assertEquals(100.0, response.getMonto());
    }

    @Test
    void crearDonacion_UsuarioNotFound() {
        DonacionRequest request = new DonacionRequest();
        request.setUsuarioId(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> donacionService.crearDonacion(request));
    }

    @Test
    void actualizarEstado_Success() {
        // 1. Preparar datos de prueba completos
        Long donacionId = 1L;
        String nuevoEstado = "CONFIRMADA";
        
        // Crear todas las entidades relacionadas
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        
        Organizacion organizacion = new Organizacion();
        organizacion.setId(1L);
        
        Campania campania = new Campania();
        campania.setId(1L);
        campania.setOrganizacion(organizacion);
        
        Donacion donacion = new Donacion();
        donacion.setId(donacionId);
        donacion.setUsuario(usuario);
        donacion.setCampania(campania);
        donacion.setOrganizacion(organizacion); // ¡ASIGNAR ORGANIZACIÓN!
        donacion.setEstado(DonacionEstado.PENDIENTE);

        // 2. Configurar mocks
        when(donacionRepository.findById(donacionId)).thenReturn(Optional.of(donacion));
        when(donacionRepository.save(any(Donacion.class))).thenAnswer(invocation -> {
            Donacion d = invocation.getArgument(0);
            d.setEstado(DonacionEstado.valueOf(nuevoEstado));
            return d;
        });
 
        // 3. Ejecutar
        DonacionResponse response = donacionService.actualizarEstado(donacionId, nuevoEstado);

        // 4. Verificaciones completas
        assertNotNull(response);
        assertEquals(DonacionEstado.CONFIRMADA, response.getEstado());
        assertEquals(usuario.getId(), response.getUsuarioId());
        assertEquals(campania.getId(), response.getCampaniaId());
        assertEquals(organizacion.getId(), response.getOrganizacionId());
    }

    @Test
    void actualizarEstado_DonacionNotFound() {
        when(donacionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> donacionService.actualizarEstado(1L, "APROBADO"));
    }

    @Test
    void obtenerDonacionPorId_DonacionNotFound() {
        when(donacionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> donacionService.obtenerDonacionPorId(1L));
    }
}