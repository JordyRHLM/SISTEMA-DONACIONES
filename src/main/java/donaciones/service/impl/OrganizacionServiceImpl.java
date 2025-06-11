package donaciones.service.impl;

import donaciones.dto.request.OrganizacionRequest;
import donaciones.dto.response.OrganizacionResponse;
import donaciones.model.enums.EstadoOrganizacion;
import donaciones.model.Organizacion;
import donaciones.model.Usuario;
import donaciones.repository.OrganizacionRepository;
import donaciones.repository.UsuarioRepository;
import donaciones.service.IOrganizacionService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganizacionServiceImpl implements IOrganizacionService {

    private final OrganizacionRepository organizacionRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public List<OrganizacionResponse> listar() {
        return organizacionRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrganizacionResponse obtenerPorId(Long id) {
        Organizacion org = organizacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Organización no encontrada"));
        return mapToResponse(org);
    }

    @Override
    public OrganizacionResponse crear(OrganizacionRequest request) {
        if (organizacionRepository.existsByNombre(request.getNombre())) {
            throw new IllegalArgumentException("Ya existe una organización con ese nombre");
        }

        Usuario owner = usuarioRepository.findById(request.getOwner_id()) // Cambiado a 'owner'
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Organizacion organizacion = Organizacion.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .estado(EstadoOrganizacion.valueOf(request.getEstado()))
                .owner(owner) // CORREGIDO: Usar 'owner' en lugar de 'usuario'
                .build();

        return mapToResponse(organizacionRepository.save(organizacion));
    }

    @Override
    public OrganizacionResponse actualizar(Long id, OrganizacionRequest request) {
        Organizacion org = organizacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Organización no encontrada"));

        Usuario owner = usuarioRepository.findById(request.getOwner_id()) // Cambiado a 'owner'
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        org.setNombre(request.getNombre());
        org.setDescripcion(request.getDescripcion());
        org.setEstado(EstadoOrganizacion.valueOf(request.getEstado()));
        org.setOwner(owner); // CORREGIDO: Usar setOwner()

        return mapToResponse(organizacionRepository.save(org));
    }

    @Override
    public void eliminar(Long id) {
        if (!organizacionRepository.existsById(id)) {
            throw new EntityNotFoundException("Organización no encontrada");
        }
        organizacionRepository.deleteById(id);
    }

    @Override
    public OrganizacionResponse darBaja(Long id) {
        Organizacion org = organizacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Organización no encontrada"));

        // Aquí debes decidir a qué estado de tu enum EstadoOrganizacion quieres cambiar
        // al dar de baja. Basado en tu script SQL, no hay un estado "INACTIVA",
        // pero sí "RECHAZADA". Si quieres un estado específico para "baja", debes
        // agregarlo al enum y la BD. Por ahora, voy a usar "RECHAZADA" como ejemplo
        // o si prefieres, puedes cambiarlo a "APROBADA" o "PENDIENTE" si tienen sentido.
        // Si no tienes un estado INACTIVA en tu enum, esto generará un error.
        // Lo ideal sería que EstadoOrganizacion tenga los estados que realmente manejas.
        org.setEstado(EstadoOrganizacion.RECHAZADA); // O EstadoOrganizacion.INACTIVA si lo agregas al enum
        return mapToResponse(organizacionRepository.save(org));
    }

    @Override
    public OrganizacionResponse activar(Long id) {
        Organizacion org = organizacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Organización no encontrada"));

        // Similar al caso anterior, si no tienes un estado "ACTIVA" en tu enum,
        // esto generará un error. Usa un estado válido de tu enum.
        org.setEstado(EstadoOrganizacion.APROBADA); // O EstadoOrganizacion.ACTIVA si lo agregas al enum
        return mapToResponse(organizacionRepository.save(org));
    }

    // ---------------------
    private OrganizacionResponse mapToResponse(Organizacion org) {
        return new OrganizacionResponse(
                org.getId(),
                org.getNombre(),
                org.getDescripcion(),
                org.getEstado().name(),
                org.getOwner().getId(), // CORREGIDO: Acceder a owner y luego a su ID
                org.getCreatedAt() // CORREGIDO: Usar getCreatedAt()
        );
    }

    @Override
    public Long obtenerDueno(Long id) {
        Organizacion org = organizacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Organización no encontrada"));
        return org.getOwner().getId();
    }
}