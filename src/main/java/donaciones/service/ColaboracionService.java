package donaciones.service;

import donaciones.dto.request.ColaboracionRequest;
import donaciones.dto.response.ColaboracionResponse;
import java.util.List;

public interface ColaboracionService {
    ColaboracionResponse crearColaboracion(ColaboracionRequest request);
    List<ColaboracionResponse> listarPorCampania(Long campaniaId);
    List<ColaboracionResponse> listarPorUsuario(Long usuarioId);
    void eliminarColaboracion(Long usuarioId, Long campaniaId);
    boolean existeColaboracion(Long usuarioId, Long campaniaId);
}