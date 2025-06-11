package donaciones.dto.request;

import donaciones.model.enums.TipoDonacion;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DonacionRequestTest {

    @Test
    void testSetAndGetTipo() {
        DonacionRequest request = new DonacionRequest();
        TipoDonacion tipo = TipoDonacion.ALIMENTOS;
        request.setTipo(tipo);
        assertEquals(tipo, request.getTipo());
    }

    @Test
    void testSetAndGetMonto() {
        DonacionRequest request = new DonacionRequest();
        Double monto = 100.0;
        request.setMonto(monto);
        assertEquals(monto, request.getMonto());
    }

    @Test
    void testSetAndGetItems() {
        DonacionRequest request = new DonacionRequest();
        String items = "{\"item1\": \"value1\"}";
        request.setItems(items);
        assertEquals(items, request.getItems());
    }

    @Test
    void testSetAndGetUsuarioId() {
        DonacionRequest request = new DonacionRequest();
        Long usuarioId = 123L;
        request.setUsuarioId(usuarioId);
        assertEquals(usuarioId, request.getUsuarioId());
    }

    @Test
    void testSetAndGetIsAnonima() {
        DonacionRequest request = new DonacionRequest();
        Boolean isAnonima = true;
        request.setIsAnonima(isAnonima);
        assertEquals(isAnonima, request.getIsAnonima());
    }
}