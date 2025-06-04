package donaciones.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizacionResponse {

    private Long id;

    private String nombre;

    private String descripcion;

    private String estado;

    private Long owner_id;

    private LocalDateTime createdAt; // CORREGIDO: Nombre de campo para consistencia con la entidad
}