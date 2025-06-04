package donaciones.dto.response;

import donaciones.model.enums.CampaniaEstado;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaniaResponse {
    private Long id;
    private String titulo;
    private String descripcion;
    private Long organizacionId;
    private String organizacionNombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private BigDecimal metaMonetaria;
    private String metaItems; // JSON como String
    private CampaniaEstado estado;
    private LocalDateTime createdAt;
}