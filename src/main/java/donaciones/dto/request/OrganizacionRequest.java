package donaciones.dto.request;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizacionRequest {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 100, message = "El nombre no debe exceder los 100 caracteres")
    private String nombre;

    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

    @NotBlank(message = "El estado es obligatorio")
    // CORREGIDO: Patrón para que coincida con el enum EstadoOrganizacion (PENDIENTE, APROBADA, RECHAZADA)
    @Pattern(regexp = "PENDIENTE|APROBADA|RECHAZADA", message = "Estado no válido. Valores permitidos: PENDIENTE, APROBADA, RECHAZADA.")
    private String estado;

    @NotNull(message = "El id del usuario es obligatorio")
    private Long owner_id;
}