package donaciones.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import donaciones.dto.CampaniaDTO;
import donaciones.repository.CampaniaRepository;
import donaciones.model.Campania;
import donaciones.service.ICampaniaService;

@Service
public class CampaniaServiceImpl implements ICampaniaService {
    @Autowired
    private CampaniaRepository campaniaRepository;

    @Override
    public List<CampaniaDTO> obtenerTodasLasCampanias() {
        return campaniaRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
        public CampaniaDTO crearCampania(CampaniaDTO campaniaDTO) {
            Campania campania = convertToEntity(campaniaDTO);
            Campania savedCampania = campaniaRepository.save(campania);
            return convertToDTO(savedCampania);
        }

    private CampaniaDTO convertToDTO(Campania campania) {
        return CampaniaDTO.builder()
                .id(campania.getId())
                .titulo(campania.getTitulo())
                .descripcion(campania.getDescripcion())
                .fechaInicio(campania.getFechaInicio())
                .fechaFin(campania.getFechaFin())
                .build();
    }

    private Campania convertToEntity(CampaniaDTO dto) {
        return Campania.builder()
                .id(dto.getId())
                .titulo(dto.getTitulo())
                .descripcion(dto.getDescripcion())
                .fechaInicio(dto.getFechaInicio())
                .fechaFin(dto.getFechaFin())
                .build();
    }
}