package donaciones.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {
    private Long id;
    private String nombre;
    private String email;
    private Boolean isAdmin;
    private Boolean isOrgOwner;
    private LocalDateTime createdAt;
}