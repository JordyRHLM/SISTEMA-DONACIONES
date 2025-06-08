package donaciones.service;

import donaciones.dto.request.CampaniaRequest;
import donaciones.dto.response.CampaniaRecaudacionResponse;
import donaciones.dto.response.CampaniaResponse;
import donaciones.model.enums.CampaniaEstado;
import java.util.List;

public interface CampaniaService {
    CampaniaResponse crearCampania(CampaniaRequest campaniaRequest);
    CampaniaResponse obtenerPorId(Long id);
    List<CampaniaResponse> obtenerTodas();
    List<CampaniaResponse> obtenerPorOrganizacion(Long organizacionId);
    CampaniaResponse actualizarCampania(Long id, CampaniaRequest campaniaRequest);
    void cambiarEstado(Long id, CampaniaEstado estado);
    void eliminarCampania(Long id);

    CampaniaRecaudacionResponse calcularRecaudacion(Long campaniaId);
}