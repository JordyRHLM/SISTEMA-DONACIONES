package donaciones.validation;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import donaciones.dto.OrganizacionDTO;
import donaciones.repository.OrganizacionRepository;

@Component
public class OrganizacionValidator {
    private final OrganizacionRepository organizacionRepository;

    public OrganizacionValidator(OrganizacionRepository organizacionRepository) {
        this.organizacionRepository = organizacionRepository;
    }

    public boolean isValidOrganizacionName(String nombre) {
        return nombre != null && !nombre.trim().isEmpty() && nombre.length() <= 50 && !organizacionRepository.existsByNombre(nombre);
    }

    public boolean isValidOrganizacionDescription(String descripcion) {
        return descripcion != null && !descripcion.trim().isEmpty() && descripcion.length() <= 100;
    }

    public boolean isValidOrganizacionTelefono(String telefono) {
        return telefono != null && !telefono.trim().isEmpty() && telefono.length() <= 15 && !organizacionRepository.existsByTelefono(telefono);
    }

    public void validaEmailUnico(String email) {
        if (organizacionRepository.existsByEmailContacto(email)) {
            throw new IllegalArgumentException("Ya existe un usuario con este email");
        }
    }

    public void validaDominioEmail(String email) {
        String dominio = email.substring(email.indexOf('@') + 1);
        List<String> dominiosBloqueados = Arrays.asList("dominiobloqueado.com", "spam.com");

        if (dominiosBloqueados.contains(dominio)) {
            throw new IllegalArgumentException("El dominio de email no está permitido");
        }
    }

    public void completeValitationOrganizacion(OrganizacionDTO organizacionDTO) {
        if (!isValidOrganizacionName(organizacionDTO.getNombre())) {
            throw new IllegalArgumentException("Nombre de organización inválido o ya existe");
        }
        if (!isValidOrganizacionDescription(organizacionDTO.getDescripcion())) {
            throw new IllegalArgumentException("Descripción de organización inválida");
        }
        if (!isValidOrganizacionTelefono(organizacionDTO.getTelefono())) {
            throw new IllegalArgumentException("Teléfono de organización inválido o ya existe");
        }
        validaEmailUnico(organizacionDTO.getEmailContacto());
        validaDominioEmail(organizacionDTO.getEmailContacto());
    }

    public class BusinessException extends RuntimeException {
        public BusinessException(String message) {
            super(message);
        }
    }
}