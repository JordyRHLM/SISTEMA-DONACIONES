package donaciones.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoluntarioCampaniaDTO {
    private Long id;
    private Long usuarioId;
    private String usuarioNombre;
    private Long campaniaId;
    private String campaniaNombre;
    private LocalDateTime fechaRegistro;
    private Integer horasAportadas;
    private String rolVoluntario;

    public VoluntarioCampaniaDTO(Long usuarioId, Long campaniaId, String rolVoluntario) {
        this.usuarioId = usuarioId;
        this.campaniaId = campaniaId;
        this.rolVoluntario = rolVoluntario;
    }
}
