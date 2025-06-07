package donaciones.dto.request;

import donaciones.model.enums.RolColaboracion;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColaboracionRequest {
    private Long usuarioId;
    private Long campaniaId;
    private RolColaboracion rol;
}