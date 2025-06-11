package donaciones.dto.request;

import donaciones.model.enums.EstadoOrganizacion;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OrganizacionRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testOrganizacionRequest_Valid() {
        OrganizacionRequest request = new OrganizacionRequest();
        request.setNombre("Valid Name");
        request.setDescripcion("Valid Description");
        request.setEstado("PENDIENTE");  // Pasa el String correcto
        request.setOwner_id(1L);

        Set<ConstraintViolation<OrganizacionRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty(), "No debería haber violaciones con datos válidos");
    }


    @Test
    void testOrganizacionRequest_InvalidName_NotBlank() {
        OrganizacionRequest request = new OrganizacionRequest();
        request.setNombre("");  // Nombre vacío para forzar la violación
        request.setDescripcion("Valid Description");
        request.setEstado("PENDIENTE");
        request.setOwner_id(1L);

        Set<ConstraintViolation<OrganizacionRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size(), "Debería haber 1 violación por nombre vacío");
        
        ConstraintViolation<OrganizacionRequest> violation = violations.iterator().next();
        assertEquals("nombre", violation.getPropertyPath().toString(), "La violación debe ser en el campo nombre");
        assertEquals("El nombre no puede estar vacío", violation.getMessage());
    }

    @Test
    void testOrganizacionRequest_InvalidEstado_Null() {
        OrganizacionRequest request = new OrganizacionRequest();
        request.setNombre("Valid Name");
        request.setDescripcion("Valid Description");
        request.setEstado(null);  // Estado nulo para forzar la violación
        request.setOwner_id(1L);

        Set<ConstraintViolation<OrganizacionRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size(), "Debería haber 1 violación por estado nulo");
        
        ConstraintViolation<OrganizacionRequest> violation = violations.iterator().next();
        assertEquals("estado", violation.getPropertyPath().toString());
        assertEquals("El estado es obligatorio", violation.getMessage());
    }

    @Test
    void testOrganizacionRequest_InvalidOwnerId_NotNull() {
        OrganizacionRequest request = new OrganizacionRequest();
        request.setNombre("Valid Name");
        request.setDescripcion("Valid Description");
        request.setEstado("PENDIENTE");
        request.setOwner_id(null);  // Owner_id nulo para forzar la violación

        Set<ConstraintViolation<OrganizacionRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size(), "Debería haber 1 violación por owner_id nulo");
        
        ConstraintViolation<OrganizacionRequest> violation = violations.iterator().next();
        assertEquals("owner_id", violation.getPropertyPath().toString());
        assertEquals("El id del usuario es obligatorio", violation.getMessage());
    }

    @Test
    void testOrganizacionRequest_InvalidName_Size() {
        OrganizacionRequest request = new OrganizacionRequest();
        request.setNombre("a".repeat(101));  // Nombre de 101 caracteres
        request.setDescripcion("Valid Description");
        request.setEstado("PENDIENTE");
        request.setOwner_id(1L);

        Set<ConstraintViolation<OrganizacionRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size(), "Debería haber 1 violación por tamaño excedido");
        
        ConstraintViolation<OrganizacionRequest> violation = violations.iterator().next();
        assertEquals("nombre", violation.getPropertyPath().toString());
        assertEquals("El nombre no debe exceder los 100 caracteres", violation.getMessage());
    }
}