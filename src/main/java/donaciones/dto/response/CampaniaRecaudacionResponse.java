package donaciones.dto.response;

import java.math.BigDecimal;
import java.util.Map;

import lombok.Data;

@Data
public class CampaniaRecaudacionResponse {

    private BigDecimal totalRecaudado;
    private BigDecimal metaMonetaria;
    private double porcentajeRecaudadoMonetario;
    private Map<String, Double> itemsRecaudados;
}