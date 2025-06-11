package donaciones.service;

import donaciones.dto.request.DonacionRequest;
import donaciones.dto.response.DonacionResponse;

import java.math.BigDecimal;
import java.util.List;

public interface DonacionService {
    DonacionResponse actualizarEstado(Long id, String estado);
    List<DonacionResponse> listarDonacionesPorUsuario(Long usuarioId);
    List<DonacionResponse> listarDonacionesPorCampania(Long campaniaId);
    DonacionResponse obtenerDonacionPorId(Long id);
    List<DonacionResponse> listarDonacionesPorOrganizacion(Long organizacionId, String estado);
    //editar donacion por id
    DonacionResponse editarDonacionPorId(Long id, Long organizacionId, DonacionRequest request);
    //asignar campania a donacion
    List<DonacionResponse> asignarCampaniaADonacion(Long campaniaId, Long organizacionId);
    //eliminar donacion por id
    void eliminarDonacionPorId(Long id);
    //listar todas las donaciones
    List<DonacionResponse> listarTodasLasDonaciones();
    //crear donacion
    DonacionResponse crearDonacion(DonacionRequest request);
    //dashboard
    BigDecimal getTotalConfirmedDonations();
}