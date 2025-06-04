package donaciones.dto.response;

import donaciones.model.enums.DonacionEstado;
import donaciones.model.enums.TipoDonacion;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DonacionResponse {
    private Long id;
    private TipoDonacion tipo;
    private Double monto;
    private String items;
    private Long usuarioId;
    private Long campaniaId;
    private Long organizacionId;
    private DonacionEstado estado;
    private Boolean isAnonima;
    private LocalDateTime createdAt;
}