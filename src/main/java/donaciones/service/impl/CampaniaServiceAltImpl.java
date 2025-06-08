package donaciones.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import donaciones.dto.request.CampaniaRequest;
import donaciones.dto.response.CampaniaRecaudacionResponse;
import donaciones.dto.response.CampaniaResponse;
import donaciones.exception.RecursoNoEncontradoException;
import donaciones.model.Campania;
import donaciones.model.Donacion;
import donaciones.model.Organizacion;
import donaciones.model.enums.DonacionEstado;
import donaciones.repository.CampaniaRepository;
import donaciones.repository.DonacionRepository;
import donaciones.repository.OrganizacionRepository;
import donaciones.service.CampaniaService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
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
    private final DonacionRepository donacionRepository;

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
     //--> CRICKO CALCULAR RECAUDACION
    public CampaniaRecaudacionResponse calcularRecaudacion(Long campaniaId) {
        Campania campania = campaniaRepository.findById(campaniaId)
            .orElseThrow(() -> new RuntimeException("Campaña no encontrada"));

        List<Donacion> donaciones = donacionRepository.findByCampaniaIdAndEstadoIn(
            campaniaId, List.of(DonacionEstado.CONFIRMADA, DonacionEstado.ENTREGADA)
        );


        double totalRecaudado = donaciones.stream()
            .filter(d -> d.getMonto() != null)
            .mapToDouble(Donacion::getMonto)
            .sum();

        BigDecimal totalRecaudadoBD = BigDecimal.valueOf(totalRecaudado);
        BigDecimal meta = campania.getMetaMonetaria() != null ? campania.getMetaMonetaria() : BigDecimal.ZERO;

        double porcentajeMonetario = meta.compareTo(BigDecimal.ZERO) > 0
            ? totalRecaudadoBD.divide(meta, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue()
            : 0;

        Map<String, Double> acumuladoItems = new HashMap<>();
        for (Donacion d : donaciones) {
            if (d.getItems() != null) {
                try {
                    Map<String, String> items = new ObjectMapper().readValue(d.getItems(), new TypeReference<>() {});
                    for (Map.Entry<String, String> entry : items.entrySet()) {
                        String item = entry.getKey();
                        double cantidad = extraerCantidad(entry.getValue());
                        acumuladoItems.merge(item, cantidad, Double::sum);
                    }
                } catch (Exception e) {
                    // Manejo de errores de parseo opcional
                }
            }
        }

        Map<String, Double> porcentajeItems = new HashMap<>();
        Map<String, Object> metaItemsMap = campania.getMetaItemsMap();
        if (metaItemsMap != null) {
            for (Map.Entry<String, Object> entry : metaItemsMap.entrySet()) {
                String item = entry.getKey();
                double metaItemCantidad = extraerCantidad(entry.getValue().toString());
                double recaudado = acumuladoItems.getOrDefault(item, 0.0);
                double porcentaje = metaItemCantidad > 0 ? (recaudado / metaItemCantidad) * 100 : 0;
                porcentajeItems.put(item, porcentaje);
            }
        }

        CampaniaRecaudacionResponse response = new CampaniaRecaudacionResponse();
        response.setTotalRecaudado(totalRecaudadoBD);
        response.setMetaMonetaria(meta);
        response.setPorcentajeRecaudadoMonetario(porcentajeMonetario);
        response.setItemsRecaudados(acumuladoItems); // <--- este es el nuevo campo
        

        return response;
    }

    private double extraerCantidad(String valor) {
        try {
            String numero = valor.replaceAll("[^0-9.]", "");
            return Double.parseDouble(numero);
        } catch (Exception e) {
            return 0;
        }
    }
}