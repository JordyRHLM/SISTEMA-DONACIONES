// Colaboracion.java
package donaciones.model;

import donaciones.model.enums.RolColaboracion;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "colaboraciones")
@IdClass(ColaboracionId.class)
public class Colaboracion {

    @Id
    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    @Id
    @ManyToOne
    @JoinColumn(name = "campania_id", referencedColumnName = "id")
    private Campania campania;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolColaboracion rol;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}

