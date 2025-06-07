// ColaboracionId.java (clase embedable para la clave compuesta)
package donaciones.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ColaboracionId implements Serializable {
    private Long usuario;
    private Long campania;

    // constructores, equals, hashCode
}