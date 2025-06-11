package donaciones.dto.response;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CampaniaRecaudacionResponseTest {

    @Test
    void testGettersAndSetters() {
        CampaniaRecaudacionResponse response = new CampaniaRecaudacionResponse();
        BigDecimal totalRecaudado = new BigDecimal("100.00");
        BigDecimal metaMonetaria = new BigDecimal("200.00");
        double porcentajeRecaudadoMonetario = 50.0;
        Map<String, Double> itemsRecaudados = new HashMap<>();
        itemsRecaudados.put("item1", 10.0);

        response.setTotalRecaudado(totalRecaudado);
        response.setMetaMonetaria(metaMonetaria);
        response.setPorcentajeRecaudadoMonetario(porcentajeRecaudadoMonetario);
        response.setItemsRecaudados(itemsRecaudados);

        assertEquals(totalRecaudado, response.getTotalRecaudado());
        assertEquals(metaMonetaria, response.getMetaMonetaria());
        assertEquals(porcentajeRecaudadoMonetario, response.getPorcentajeRecaudadoMonetario());
        assertEquals(itemsRecaudados, response.getItemsRecaudados());
    }

    @Test
    void testNoArgsConstructor() {
        CampaniaRecaudacionResponse response = new CampaniaRecaudacionResponse();
        assertNull(response.getTotalRecaudado());
        assertNull(response.getMetaMonetaria());
        assertEquals(0.0, response.getPorcentajeRecaudadoMonetario());
        assertNull(response.getItemsRecaudados());
    }

    @Test
    void testSettersWithNullValues() {
        CampaniaRecaudacionResponse response = new CampaniaRecaudacionResponse();
        response.setTotalRecaudado(null);
        response.setMetaMonetaria(null);
        response.setItemsRecaudados(null);

        assertNull(response.getTotalRecaudado());
        assertNull(response.getMetaMonetaria());
        assertNull(response.getItemsRecaudados());
    }

    @Test
    void testEmptyItemsRecaudados() {
        CampaniaRecaudacionResponse response = new CampaniaRecaudacionResponse();
        Map<String, Double> itemsRecaudados = new HashMap<>();
        response.setItemsRecaudados(itemsRecaudados);
        assertEquals(itemsRecaudados, response.getItemsRecaudados());
    }

    @Test
    void testPorcentajeRecaudadoMonetarioZero() {
        CampaniaRecaudacionResponse response = new CampaniaRecaudacionResponse();
        response.setPorcentajeRecaudadoMonetario(0.0);
        assertEquals(0.0, response.getPorcentajeRecaudadoMonetario());
    }
}