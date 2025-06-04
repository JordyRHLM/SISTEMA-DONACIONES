package donaciones.service;

import donaciones.model.VoluntarioCampania;
import java.util.List;

public interface IVoluntarioCampaniaService {
    VoluntarioCampania registrarVoluntarioEnCampania(VoluntarioCampania voluntarioCampania);
    void eliminarVoluntarioDeCampania(Long id);
    List<VoluntarioCampania> listarVoluntariosPorCampania(Long campaniaId);
    List<VoluntarioCampania> listarCampaniasPorVoluntario(Long usuarioId);
    boolean esVoluntarioEnCampania(Long usuarioId, Long campaniaId);
    VoluntarioCampania actualizarVoluntarioEnCampania(VoluntarioCampania voluntarioCampania);
    VoluntarioCampania obtenerPorId(Long id);
}