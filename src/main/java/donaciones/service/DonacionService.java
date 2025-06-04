package donaciones.service;

import donaciones.dto.request.DonacionRequest;
import donaciones.dto.response.DonacionResponse;
import java.util.List;

public interface DonacionService {
    DonacionResponse crearDonacion(DonacionRequest request);
    DonacionResponse actualizarEstado(Long id, String estado);
    List<DonacionResponse> listarDonacionesPorUsuario(Long usuarioId);
    List<DonacionResponse> listarDonacionesPorCampania(Long campaniaId);
    DonacionResponse obtenerDonacionPorId(Long id);
}