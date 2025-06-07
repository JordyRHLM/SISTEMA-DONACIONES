package donaciones.dto.response;

import donaciones.model.enums.RolColaboracion;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ColaboracionResponse {
    private Long usuarioId;
    private String usuarioNombre;
    private Long campaniaId;
    private String campaniaTitulo;
    private RolColaboracion rol;
    private LocalDateTime fechaColaboracion;
}