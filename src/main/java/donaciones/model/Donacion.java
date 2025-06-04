package donaciones.model;

import donaciones.model.enums.DonacionEstado;
import donaciones.model.enums.TipoDonacion;
import donaciones.model.Usuario;
import donaciones.model.Campania;
import donaciones.model.Organizacion;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "donaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Donacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private TipoDonacion tipo;
    
    @Positive
    private Double monto;
    
    private String items; // JSON con los items donados
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campania_id")
    private Campania campania;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacion_id")
    private Organizacion organizacion;
    
    @Enumerated(EnumType.STRING)
    private DonacionEstado estado = DonacionEstado.PENDIENTE;
}