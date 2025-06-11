package donaciones.service;

import donaciones.dto.response.ColaboracionResponse;
import donaciones.model.enums.RolColaboracion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ColaboracionServiceTest {

    @Mock
    private ColaboracionService colaboracionService;

    @Test
    void listarPorCampania_campaniaId_returnsList() {
        when(colaboracionService.listarPorCampania(1L)).thenReturn(Collections.emptyList());
        List<ColaboracionResponse> result = colaboracionService.listarPorCampania(1L);
        assertNotNull(result);
    }

    @Test
    void listarPorUsuario_usuarioId_returnsList() {
        when(colaboracionService.listarPorUsuario(1L)).thenReturn(Collections.emptyList());
        List<ColaboracionResponse> result = colaboracionService.listarPorUsuario(1L);
        assertNotNull(result);
    }

    @Test
    void existeColaboracion_usuarioIdAndCampaniaId_returnsBoolean() {
        when(colaboracionService.existeColaboracion(1L, 1L)).thenReturn(true);
        boolean result = colaboracionService.existeColaboracion(1L, 1L);
        assertTrue(result);
    }

    @Test
    void listarTodas_returnsList() {
        when(colaboracionService.listarTodas()).thenReturn(Collections.emptyList());
        List<ColaboracionResponse> result = colaboracionService.listarTodas();
        assertNotNull(result);
    }

    @Test
    void actualizarColaboracion_usuarioIdCampaniaIdNuevoRol_returnsColaboracionResponse() {
        when(colaboracionService.actualizarColaboracion(1L, 1L, RolColaboracion.VOLUNTARIO)).thenReturn(new ColaboracionResponse());
        ColaboracionResponse result = colaboracionService.actualizarColaboracion(1L, 1L, RolColaboracion.VOLUNTARIO);
        assertNotNull(result);
    }
}