package donaciones.service.impl;

import donaciones.dto.request.ColaboracionRequest;
import donaciones.dto.response.ColaboracionResponse;
import donaciones.exception.RecursoNoEncontradoException;
import donaciones.exception.ValidacionException;
import donaciones.model.*;
import donaciones.model.enums.RolColaboracion;
import donaciones.repository.*;
import donaciones.service.ColaboracionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ColaboracionServiceImpl implements ColaboracionService {

    private final ColaboracionRepository colaboracionRepository;
    private final UsuarioRepository usuarioRepository;
    private final CampaniaRepository campaniaRepository;
    private final OrganizacionRepository organizacionRepository;

    @Override
    @Transactional
    public ColaboracionResponse crearColaboracion(ColaboracionRequest request) {
        // Validar que el usuario exista
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));
        
        // Validar que la campaña exista
        Campania campania = campaniaRepository.findById(request.getCampaniaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Campaña no encontrada"));
        
        // Validar roles no permitidos usando los campos booleanos directamente
        if(Boolean.TRUE.equals(usuario.getIsAdmin())) {
            throw new ValidacionException("Los administradores no pueden ser colaboradores");
        }
        
        if(Boolean.TRUE.equals(usuario.getIsOrgOwner())) {
            // Cargar la organización completa para verificar la propiedad
            Organizacion organizacion = organizacionRepository.findById(campania.getOrganizacion().getId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Organización no encontrada"));
            
            if(organizacion.getOwner() != null && organizacion.getOwner().getId().equals(usuario.getId())) {
                throw new ValidacionException("El dueño de la organización no puede ser colaborador en sus propias campañas");
            }
        }
        
        // Validar colaboración duplicada
        if(colaboracionRepository.existsByUsuarioIdAndCampaniaId(usuario.getId(), campania.getId())) {
            throw new ValidacionException("El usuario ya está colaborando en esta campaña");
        }
        
        // Crear la nueva colaboración
        Colaboracion colaboracion = new Colaboracion();
        colaboracion.setUsuario(usuario);
        colaboracion.setCampania(campania);
        colaboracion.setRol(request.getRol());
        
        Colaboracion saved = colaboracionRepository.save(colaboracion);
        
        return mapToResponse(saved);
    }

    @Override
    public List<ColaboracionResponse> listarPorCampania(Long campaniaId) {
        return colaboracionRepository.findByCampaniaId(campaniaId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ColaboracionResponse> listarPorUsuario(Long usuarioId) {
        return colaboracionRepository.findByUsuarioId(usuarioId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminarColaboracion(Long usuarioId, Long campaniaId) {
        if(!colaboracionRepository.existsByUsuarioIdAndCampaniaId(usuarioId, campaniaId)) {
            throw new RecursoNoEncontradoException("Colaboración no encontrada");
        }
        colaboracionRepository.deleteByUsuarioIdAndCampaniaId(usuarioId, campaniaId);
    }

    @Override
    public boolean existeColaboracion(Long usuarioId, Long campaniaId) {
        return colaboracionRepository.existsByUsuarioIdAndCampaniaId(usuarioId, campaniaId);
    }

    private ColaboracionResponse mapToResponse(Colaboracion colaboracion) {
        ColaboracionResponse response = new ColaboracionResponse();
        response.setUsuarioId(colaboracion.getUsuario().getId());
        response.setUsuarioNombre(colaboracion.getUsuario().getNombre());
        response.setCampaniaId(colaboracion.getCampania().getId());
        response.setCampaniaTitulo(colaboracion.getCampania().getTitulo());
        response.setRol(colaboracion.getRol());
        response.setFechaColaboracion(colaboracion.getCreatedAt());
        return response;
    }
    @Override
    public List<ColaboracionResponse> listarTodas() {
        return colaboracionRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ColaboracionResponse actualizarColaboracion(Long usuarioId, Long campaniaId, RolColaboracion nuevoRol) {
        // Verificar que la colaboración existe
        Colaboracion colaboracion = colaboracionRepository.findByUsuarioIdAndCampaniaId(usuarioId, campaniaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Colaboración no encontrada"));
        
        // Validar que el nuevo rol no sea nulo
        if(nuevoRol == null) {
            throw new ValidacionException("El rol no puede ser nulo");
        }
        
        // Actualizar el rol
        colaboracionRepository.actualizarRol(usuarioId, campaniaId, nuevoRol);
        
        // Obtener la colaboración actualizada para devolverla
        Colaboracion actualizada = colaboracionRepository.findByUsuarioIdAndCampaniaId(usuarioId, campaniaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error al recuperar la colaboración actualizada"));
        
        return mapToResponse(actualizada);
    }

}