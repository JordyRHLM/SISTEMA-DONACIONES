package donaciones.model;

import jakarta.persistence.*;
import java.time.LocalDateTime; 
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "voluntarios_campanias")
public class VoluntarioCampania {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "campania_id", nullable = false)
    private Campania campania;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "horas_aportadas")
    private Integer horasAportadas;

    @Column(name = "rol_voluntario")
    private String rolVoluntario;

    public VoluntarioCampania() {
        this.fechaRegistro = LocalDateTime.now();  
    }

    public VoluntarioCampania(Usuario usuario, Campania campania, String rolVoluntario) {
        this.usuario = usuario;
        this.campania = campania;
        this.rolVoluntario = rolVoluntario;
        this.fechaRegistro = LocalDateTime.now();  
    }

    @Override
    public String toString() {
        return "VoluntarioCampania{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", campania=" + campania +
                ", fechaRegistro=" + fechaRegistro +
                ", horasAportadas=" + horasAportadas +
                ", rolVoluntario='" + rolVoluntario + '\'' +
                '}';
    }
}
