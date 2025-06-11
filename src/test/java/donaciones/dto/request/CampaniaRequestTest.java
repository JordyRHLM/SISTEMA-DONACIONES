package donaciones.dto.request;

import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CampaniaRequestTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void testValidCampaniaRequest() {
        CampaniaRequest request = CampaniaRequest.builder()
                .titulo("Campaña Solidaria")
                .descripcion("Ayudemos a los necesitados")
                .organizacionId(1L)
                .fechaInicio(LocalDate.now())
                .fechaFin(LocalDate.now().plusDays(30))
                .metaMonetaria(BigDecimal.valueOf(1000))
                .metaItems(new HashMap<>())
                .build();

        Set<ConstraintViolation<CampaniaRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidCampaniaRequest_BlankTitulo() {
        CampaniaRequest request = CampaniaRequest.builder()
                .titulo("")
                .descripcion("Ayudemos a los necesitados")
                .organizacionId(1L)
                .fechaInicio(LocalDate.now())
                .fechaFin(LocalDate.now().plusDays(30))
                .metaMonetaria(BigDecimal.valueOf(1000))
                .metaItems(new HashMap<>())
                .build();

        Set<ConstraintViolation<CampaniaRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
    }

    @Test
    void testInvalidCampaniaRequest_PastFechaInicio() {
        CampaniaRequest request = CampaniaRequest.builder()
                .titulo("Campaña Solidaria")
                .descripcion("Ayudemos a los necesitados")
                .organizacionId(1L)
                .fechaInicio(LocalDate.now().minusDays(1))
                .fechaFin(LocalDate.now().plusDays(30))
                .metaMonetaria(BigDecimal.valueOf(1000))
                .metaItems(new HashMap<>())
                .build();

        Set<ConstraintViolation<CampaniaRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
    }

    @Test
    void testInvalidCampaniaRequest_PastFechaFin() {
        CampaniaRequest request = CampaniaRequest.builder()
                .titulo("Campaña Solidaria")
                .descripcion("Ayudemos a los necesitados")
                .organizacionId(1L)
                .fechaInicio(LocalDate.now())
                .fechaFin(LocalDate.now().minusDays(1))
                .metaMonetaria(BigDecimal.valueOf(1000))
                .metaItems(new HashMap<>())
                .build();

        Set<ConstraintViolation<CampaniaRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
    }

    @Test
    void testInvalidCampaniaRequest_NegativeMetaMonetaria() {
        CampaniaRequest request = CampaniaRequest.builder()
                .titulo("Campaña Solidaria")
                .descripcion("Ayudemos a los necesitados")
                .organizacionId(1L)
                .fechaInicio(LocalDate.now())
                .fechaFin(LocalDate.now().plusDays(30))
                .metaMonetaria(BigDecimal.valueOf(-1000))
                .metaItems(new HashMap<>())
                .build();

        Set<ConstraintViolation<CampaniaRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
    }
}