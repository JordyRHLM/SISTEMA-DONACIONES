package donaciones.model;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import donaciones.model.enums.CampaniaEstado; // ¡Importa tu enum!

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "campanias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Campania {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacion_id", referencedColumnName = "id")
    private Organizacion organizacion;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(name = "meta_monetaria", precision = 10, scale = 2)
    private BigDecimal metaMonetaria;

    @Column(name="meta_items", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String metaItems; // JSON como String

    // --- ¡AÑADE ESTA PROPIEDAD! ---
    @Enumerated(EnumType.STRING) // Indica a JPA que persista el enum como String (ACTIVA, BORRADOR, etc.)
    @Column(name = "estado_campania", nullable = false) // Nombre de la columna en la base de datos
    private CampaniaEstado estado; // <-- ¡Esta es la propiedad que faltaba!
    // ----------------------------

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;


    // Métodos auxiliares para manejar metaItems como Map

    private void asignarMetaItems(Campania campania, Map<String, Object> metaItems) {
        campania.setMetaItemsMap(metaItems);
    }


    public void setMetaItemsMap(Map<String, Object> metaItemsMap) {
        try {
            this.metaItems = new ObjectMapper().writeValueAsString(metaItemsMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir Map a JSON", e);
        }
    }

    public Map<String, Object> getMetaItemsMap() {
        try {
            // Manejar caso de metaItems nulo para evitar NPE si la columna es opcional
            if (this.metaItems == null) {
                return null; // O Collections.emptyMap(); según tu lógica de negocio
            }
            return new ObjectMapper().readValue(this.metaItems, new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir JSON a Map", e);
        }
    }
}