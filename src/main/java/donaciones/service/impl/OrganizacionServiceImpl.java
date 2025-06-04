package donaciones.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import donaciones.dto.OrganizacionDTO;
import donaciones.model.Organizacion;
import donaciones.repository.OrganizacionRepository;
import donaciones.service.IOrganizacionService;
import donaciones.validation.OrganizacionValidator;

import java.util.stream.Collectors;

@Service
public class OrganizacionServiceImpl implements IOrganizacionService{

    private OrganizacionRepository organizacionRepository;
    private OrganizacionValidator organizacionValidator;

    public OrganizacionServiceImpl(OrganizacionRepository organizacionRepository, OrganizacionValidator organizacionValidator) {
        this.organizacionRepository = organizacionRepository;
        this.organizacionValidator = organizacionValidator;
    }

    @Override
    public List<OrganizacionDTO> listarOrganizaciones() {
        return organizacionRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

    }
    
    public OrganizacionDTO obtenerOrganizacionPorId(Long id_org) {
        Organizacion org = organizacionRepository.findById(id_org)
                .orElseThrow(() -> new RuntimeException("Organizacion no encontrada con id: " + id_org));
        return convertToDTO(org);
    }

    
    public OrganizacionDTO crearOrganizacion(OrganizacionDTO organizacionDTO) {
        organizacionValidator.completeValitationOrganizacion(organizacionDTO);

        Organizacion organizacion = convertToEntity(organizacionDTO);
        Organizacion orgSAve = organizacionRepository.save(organizacion);
        return convertToDTO(orgSAve);
    }

    
    public OrganizacionDTO actualizarOrganizacion(Long id_org, OrganizacionDTO organizacionDTO) {
        Organizacion organizacionExistente = organizacionRepository.findById(id_org)
                .orElseThrow(() -> new RuntimeException("Organizacion no encontrada con id: " + id_org));

        organizacionExistente.setNombre(organizacionDTO.getNombre());
        organizacionExistente.setDescripcion(organizacionDTO.getDescripcion());
        organizacionExistente.setTelefono(organizacionDTO.getTelefono());
        organizacionExistente.setEmailContacto(organizacionDTO.getEmailContacto());
        organizacionExistente.setDireccion(organizacionDTO.getDireccion());
        
        Organizacion organizacionActualizada = organizacionRepository.save(organizacionExistente);
        return convertToDTO(organizacionActualizada);
    }

    
    public OrganizacionDTO eliminarOrganizacion(Long id_org) {
        Organizacion organizacion = organizacionRepository.findById(id_org)
                .orElseThrow(() -> new RuntimeException("Organizacion no encontrada con id: " + id_org));
        organizacionRepository.delete(organizacion);
        return convertToDTO(organizacion);
    }

    
    public List<OrganizacionDTO> buscarOrganizacionesPorNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre de la organización no puede ser nulo o vacío");
        }
        return organizacionRepository.findAll()
                .stream()
                .filter(org -> org.getNombre().equalsIgnoreCase(nombre))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    private OrganizacionDTO convertToDTO(Organizacion organizacion) {
        return OrganizacionDTO.builder()
                .id_org(organizacion.getId_org())
                .nombre(organizacion.getNombre())
                .descripcion(organizacion.getDescripcion())
                .telefono(organizacion.getTelefono())
                .emailContacto(organizacion.getEmailContacto())
                .direccion(organizacion.getDireccion())
                .build();
    }

    private Organizacion convertToEntity(OrganizacionDTO organizacionDTO) {
        return Organizacion.builder()
                .id_org(organizacionDTO.getId_org())
                .nombre(organizacionDTO.getNombre())
                .descripcion(organizacionDTO.getDescripcion())
                .telefono(organizacionDTO.getTelefono())
                .emailContacto(organizacionDTO.getEmailContacto())
                .direccion(organizacionDTO.getDireccion())
                .build();
    }
    
}