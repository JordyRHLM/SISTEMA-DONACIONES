package donaciones.controller;

import donaciones.dto.VoluntarioCampaniaDTO;
import donaciones.model.VoluntarioCampania;
import donaciones.model.Campania;
import donaciones.model.Usuario;
import donaciones.service.IVoluntarioCampaniaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/voluntarios-campanias")
public class VoluntarioCampaniaController {

    private final IVoluntarioCampaniaService voluntarioCampaniaService;

    @Autowired
    public VoluntarioCampaniaController(IVoluntarioCampaniaService voluntarioCampaniaService) {
        this.voluntarioCampaniaService = voluntarioCampaniaService;
    }

    @PostMapping
    public ResponseEntity<VoluntarioCampaniaDTO> registrarVoluntarioEnCampania(
            @RequestBody VoluntarioCampaniaDTO voluntarioCampaniaDTO) {
        VoluntarioCampania voluntarioCampania = convertToEntity(voluntarioCampaniaDTO);
        VoluntarioCampania saved = voluntarioCampaniaService.registrarVoluntarioEnCampania(voluntarioCampania);
        return ResponseEntity.ok(convertToDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VoluntarioCampaniaDTO> actualizarVoluntarioEnCampania(
            @PathVariable Long id,
            @RequestBody VoluntarioCampaniaDTO voluntarioCampaniaDTO) {
        VoluntarioCampania voluntarioCampania = convertToEntity(voluntarioCampaniaDTO);
        voluntarioCampania.setId(id);
        VoluntarioCampania updated = voluntarioCampaniaService.actualizarVoluntarioEnCampania(voluntarioCampania);
        return ResponseEntity.ok(convertToDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVoluntarioDeCampania(@PathVariable Long id) {
        voluntarioCampaniaService.eliminarVoluntarioDeCampania(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoluntarioCampaniaDTO> obtenerPorId(@PathVariable Long id) {
        VoluntarioCampania voluntarioCampania = voluntarioCampaniaService.obtenerPorId(id);
        return ResponseEntity.ok(convertToDTO(voluntarioCampania));
    }

    @GetMapping("/campania/{campaniaId}")
    public ResponseEntity<List<VoluntarioCampaniaDTO>> listarVoluntariosPorCampania(
            @PathVariable Long campaniaId) {
        List<VoluntarioCampania> voluntarios = voluntarioCampaniaService.listarVoluntariosPorCampania(campaniaId);
        return ResponseEntity.ok(voluntarios.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<VoluntarioCampaniaDTO>> listarCampaniasPorVoluntario(
            @PathVariable Long usuarioId) {
        List<VoluntarioCampania> campanias = voluntarioCampaniaService.listarCampaniasPorVoluntario(usuarioId);
        return ResponseEntity.ok(campanias.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/verificar")
    public ResponseEntity<Boolean> esVoluntarioEnCampania(
            @RequestParam Long usuarioId,
            @RequestParam Long campaniaId) {
        boolean existe = voluntarioCampaniaService.esVoluntarioEnCampania(usuarioId, campaniaId);
        return ResponseEntity.ok(existe);
    }

    private VoluntarioCampania convertToEntity(VoluntarioCampaniaDTO dto) {
        VoluntarioCampania entity = new VoluntarioCampania();
        entity.setUsuario(new Usuario(dto.getUsuarioId()));
        entity.setCampania(new Campania(dto.getCampaniaId())); 
        entity.setHorasAportadas(dto.getHorasAportadas());
        entity.setRolVoluntario(dto.getRolVoluntario());
        
        if(dto.getFechaRegistro() != null) {
            entity.setFechaRegistro(dto.getFechaRegistro());
        }
        
        return entity;
    }

    private VoluntarioCampaniaDTO convertToDTO(VoluntarioCampania entity) {
        VoluntarioCampaniaDTO dto = new VoluntarioCampaniaDTO();
        dto.setId(entity.getId());
        if (entity.getUsuario() != null) {
            dto.setUsuarioId(entity.getUsuario().getId());
            dto.setUsuarioNombre(entity.getUsuario().getNombre()); // Asume que Usuario tiene getNombre()
        }
        if (entity.getCampania() != null) {
            dto.setCampaniaId(entity.getCampania().getId());
            dto.setCampaniaNombre(entity.getCampania().getTitulo()); // Usamos getTitulo() en lugar de getNombre()
        }
        dto.setFechaRegistro(entity.getFechaRegistro());
        dto.setHorasAportadas(entity.getHorasAportadas());
        dto.setRolVoluntario(entity.getRolVoluntario());
        return dto;
    }
}