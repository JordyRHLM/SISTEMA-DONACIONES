package donaciones.service;

import java.util.List;
import donaciones.dto.CampaniaDTO;

public interface ICampaniaService {
    List<CampaniaDTO> obtenerTodasLasCampanias();
    CampaniaDTO crearCampania(CampaniaDTO campaniaDTO);
}