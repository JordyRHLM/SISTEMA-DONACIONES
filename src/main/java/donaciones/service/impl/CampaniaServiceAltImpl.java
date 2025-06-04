package donaciones.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import donaciones.dto.request.CampaniaRequest;
import donaciones.dto.response.CampaniaResponse;
import donaciones.exception.RecursoNoEncontradoException;
import donaciones.model.Campania;
import donaciones.model.Organizacion;
import donaciones.repository.CampaniaRepository;
import donaciones.repository.OrganizacionRepository;
import donaciones.service.CampaniaService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampaniaServiceAltImpl implements CampaniaService {

    private static final Logger logger = LoggerFactory.getLogger(CampaniaServiceAltImpl.class);

    private final CampaniaRepository campaniaRepository;
    private final OrganizacionRepository organizacionRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public CampaniaResponse crearCampania(CampaniaRequest request) {
        validarFechas(request.getFechaInicio(), request.getFechaFin());
        Organizacion organizacion = obtenerOrganizacion(request.getOrganizacionId());

        Campania campania = Campania.builder()
                .titulo(request.getTitulo())
                .descripcion(request.getDescripcion())
                .organizacion(organizacion)
                .fechaInicio(request.getFechaInicio())
                .fechaFin(request.getFechaFin())
                .metaMonetaria(request.getMetaMonetaria())
                .build();

        asignarMetaItems(campania, request.getMetaItems());

        Campania saved = campaniaRepository.save(campania);
        logger.info("Campaña creada con id: {}", saved.getId());
        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CampaniaResponse obtenerPorId(Long id) {
        Campania campania = obtenerCampaniaPorId(id);
        return mapToResponse(campania);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CampaniaResponse> obtenerTodas() {
        return campaniaRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CampaniaResponse> obtenerPorOrganizacion(Long organizacionId) {
        return campaniaRepository.findByOrganizacionId(organizacionId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CampaniaResponse actualizarCampania(Long id, CampaniaRequest request) {
        validarFechas(request.getFechaInicio(), request.getFechaFin());
        Campania campania = obtenerCampaniaPorId(id);

        campania.setTitulo(request.getTitulo());
        campania.setDescripcion(request.getDescripcion());
        campania.setFechaInicio(request.getFechaInicio());
        campania.setFechaFin(request.getFechaFin());
        campania.setMetaMonetaria(request.getMetaMonetaria());

        asignarMetaItems(campania, request.getMetaItems());

        Campania updated = campaniaRepository.save(campania);
        logger.info("Campaña actualizada con id: {}", updated.getId());
        return mapToResponse(updated);
    }

    @Transactional
    public void cambiarEstado(Long id, Object nuevoEstado) {
        throw new UnsupportedOperationException("La entidad Campania ya no maneja estados.");
    }

    @Override
    @Transactional
    public void cambiarEstado(Long id, donaciones.model.enums.CampaniaEstado nuevoEstado) {
        throw new UnsupportedOperationException("La entidad Campania ya no maneja estados.");
    }

    @Override
    @Transactional
    public void eliminarCampania(Long id) {
        Campania campania = obtenerCampaniaPorId(id);
        campaniaRepository.delete(campania);
        logger.info("Campaña eliminada con id: {}", id);
    }

    // Métodos auxiliares

    private Organizacion obtenerOrganizacion(Long id) {
        return organizacionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Organización no encontrada con id " + id));
    }

    private Campania obtenerCampaniaPorId(Long id) {
        return campaniaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Campaña no encontrada con id " + id));
    }

    private void validarFechas(java.time.LocalDate inicio, java.time.LocalDate fin) {
        if (inicio.isAfter(fin)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha fin");
        }
    }

    private void asignarMetaItems(Campania campania, Map<String, Object> metaItems) {
        if (metaItems != null) {
            try {
                String json = objectMapper.writeValueAsString(metaItems);
                campania.setMetaItems(json);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error al convertir metaItems a JSON", e);
            }
        } else {
            campania.setMetaItems(null);
        }
    }

    private CampaniaResponse mapToResponse(Campania campania) {
        return CampaniaResponse.builder()
                .id(campania.getId())
                .titulo(campania.getTitulo())
                .descripcion(campania.getDescripcion())
                .organizacionId(campania.getOrganizacion().getId())
                .organizacionNombre(campania.getOrganizacion().getNombre())
                .fechaInicio(campania.getFechaInicio())
                .fechaFin(campania.getFechaFin())
                .metaMonetaria(campania.getMetaMonetaria())
                .metaItems(campania.getMetaItems())
                // .estado eliminado
                .createdAt(campania.getCreatedAt())
                .build();
    }
}