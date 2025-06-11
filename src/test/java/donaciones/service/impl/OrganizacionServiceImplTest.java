package donaciones.service.impl;

import donaciones.dto.request.OrganizacionRequest;
import donaciones.dto.response.OrganizacionResponse;
import donaciones.model.Organizacion;
import donaciones.model.Usuario;
import donaciones.model.enums.EstadoOrganizacion;
import donaciones.repository.OrganizacionRepository;
import donaciones.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrganizacionServiceImplTest {

    @Mock
    private OrganizacionRepository organizacionRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private OrganizacionServiceImpl organizacionService;

    @Test
    void listar_shouldReturnEmptyList_whenNoOrganizacionesExist() {
        when(organizacionRepository.findAll()).thenReturn(Collections.emptyList());
        List<OrganizacionResponse> result = organizacionService.listar();
        assertTrue(result.isEmpty());
    }

    @Test
    void obtenerPorId_shouldReturnOrganizacionResponse_whenOrganizacionExists() {
        Organizacion organizacion = Organizacion.builder().id(1L).nombre("Test Org").descripcion("Desc").estado(EstadoOrganizacion.APROBADA).owner(Usuario.builder().id(1L).build()).build();
        when(organizacionRepository.findById(1L)).thenReturn(Optional.of(organizacion));
        OrganizacionResponse result = organizacionService.obtenerPorId(1L);
        assertEquals("Test Org", result.getNombre());
    }

    @Test
    void crear_shouldCreateOrganizacion_whenRequestIsValid() {
        // Configuración
        OrganizacionRequest request = new OrganizacionRequest();
        request.setNombre("New Org");
        request.setDescripcion("Desc");
        request.setEstado("APROBADA");  // es String, no enum
        request.setOwner_id(1L);

        Usuario owner = Usuario.builder().id(1L).build();
        Organizacion organizacionGuardada = Organizacion.builder()
            .id(1L)
            .nombre("New Org")
            .descripcion("Desc")
            .estado(EstadoOrganizacion.APROBADA)  // enum en entidad
            .owner(owner)
            .createdAt(LocalDateTime.now())
            .build();

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(owner));
        when(organizacionRepository.existsByNombre("New Org")).thenReturn(false);
        when(organizacionRepository.save(any(Organizacion.class))).thenReturn(organizacionGuardada);

        // Ejecución
        OrganizacionResponse result = organizacionService.crear(request);

        // Verificaciones
        assertAll(
            () -> assertEquals(1L, result.getId()),
            () -> assertEquals("New Org", result.getNombre()),
            () -> assertEquals("Desc", result.getDescripcion()),
            () -> assertEquals("APROBADA", result.getEstado()),  // comparar como String
            //() -> assertEquals(1L, result.getOwner_id()),
            () -> assertNotNull(result.getCreatedAt())
        );
    }


    @Test
void actualizar_shouldUpdateOrganizacion_whenOrganizacionExists() {
    OrganizacionRequest request = new OrganizacionRequest();
    request.setNombre("New Org");
    request.setDescripcion("Desc");
    request.setEstado("APROBADA"); // Como es String, usar valor correcto en mayúsculas
    request.setOwner_id(1L);

    Organizacion existingOrg = Organizacion.builder()
        .id(1L)
        .nombre("Old Org")
        .descripcion("Desc")
        .estado(EstadoOrganizacion.PENDIENTE)
        .owner(Usuario.builder().id(1L).build())
        .build();

    Usuario owner = Usuario.builder().id(1L).build();

    when(organizacionRepository.findById(1L)).thenReturn(Optional.of(existingOrg));
    when(usuarioRepository.findById(1L)).thenReturn(Optional.of(owner));
    when(organizacionRepository.save(any(Organizacion.class))).thenReturn(
        Organizacion.builder()
            .id(1L)
            .nombre("Updated Org")
            .descripcion("Desc")
            .estado(EstadoOrganizacion.APROBADA)
            .owner(owner)
            .build()
    );

    OrganizacionResponse result = organizacionService.actualizar(1L, request);

    assertEquals("Updated Org", result.getNombre());
}

}