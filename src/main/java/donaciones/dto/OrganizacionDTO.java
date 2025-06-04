package donaciones.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizacionDTO implements Serializable{

    private Long id_org;
    
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 100, message = "La descripción no puede exceder los 100 caracteres")
    private String descripcion;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "El teléfono debe ser un número válido")
    private String telefono;

    @NotBlank(message = "El email de contacto no puede estar vacío")
    @Email(message = "El email de contacto debe ser válido")
    @Size(max = 50, message = "El email de contacto no puede exceder los 50 caracteres")
    @JsonProperty("email_contacto")
    private String emailContacto;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(max = 100, message = "La dirección no puede exceder los 100 caracteres")
    private String direccion;
}