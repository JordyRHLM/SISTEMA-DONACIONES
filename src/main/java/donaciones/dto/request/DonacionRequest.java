package donaciones.dto.request;

import donaciones.model.enums.TipoDonacion;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class DonacionRequest {
    private TipoDonacion tipo;
    
    @Positive(message = "El monto debe ser positivo")
    private Double monto;
    
    private String items; // JSON de items donados (si aplica)
    private Long usuarioId;
    private Long campaniaId;
    private Long organizacionId;
    private Boolean isAnonima;
}