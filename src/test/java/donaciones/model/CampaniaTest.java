package donaciones.model;

import donaciones.model.enums.CampaniaEstado;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CampaniaTest {

    @Test
    void testSetAndGetMetaItemsMap() {
        Campania campania = new Campania();
        Map<String, Object> metaItemsMap = new HashMap<>();
        metaItemsMap.put("item1", 10);
        metaItemsMap.put("item2", "test");

        campania.setMetaItemsMap(metaItemsMap);
        Map<String, Object> retrievedMap = campania.getMetaItemsMap();

        assertEquals(metaItemsMap, retrievedMap);
    }

    @Test
    void testSetAndGetEstado() {
        Campania campania = new Campania();
        campania.setEstado(CampaniaEstado.ACTIVA);
        assertEquals(CampaniaEstado.ACTIVA, campania.getEstado());
    }

    @Test
    void testBuilder() {
        Campania campania = Campania.builder()
                .titulo("Test Campaign")
                .descripcion("Test Description")
                .fechaInicio(LocalDate.now())
                .fechaFin(LocalDate.now().plusDays(30))
                .metaMonetaria(BigDecimal.valueOf(1000))
                .estado(CampaniaEstado.BORRADOR)
                .build();

        assertEquals("Test Campaign", campania.getTitulo());
        assertEquals("Test Description", campania.getDescripcion());
        assertEquals(CampaniaEstado.BORRADOR, campania.getEstado());
    }

    @Test
    void testNoArgsConstructor() {
        Campania campania = new Campania();
        assertNull(campania.getTitulo());
    }

    @Test
    void testGetMetaItemsMap_nullMetaItems() {
        Campania campania = new Campania();
        campania.setMetaItems(null);
        assertNull(campania.getMetaItemsMap());
    }
}