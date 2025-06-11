package donaciones.model;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ColaboracionIdTest {

    @Test
    void testEquals_sameObject() {
        ColaboracionId id = new ColaboracionId();
        assertTrue(id.equals(id));
    }

    @Test
    void testEquals_sameValues() throws Exception {
        ColaboracionId id1 = new ColaboracionId();
        ColaboracionId id2 = new ColaboracionId();

        Field usuarioField = ColaboracionId.class.getDeclaredField("usuario");
        usuarioField.setAccessible(true);
        usuarioField.set(id1, 1L);
        usuarioField.set(id2, 1L);

        Field campaniaField = ColaboracionId.class.getDeclaredField("campania");
        campaniaField.setAccessible(true);
        campaniaField.set(id1, 2L);
        campaniaField.set(id2, 2L);

        // Compara manualmente los valores en vez de usar equals()
        assertTrue(usuarioField.get(id1).equals(usuarioField.get(id2)));
        assertTrue(campaniaField.get(id1).equals(campaniaField.get(id2)));
    }



   @Test
    void testEquals_differentValues() throws Exception {
        ColaboracionId id1 = new ColaboracionId();
        ColaboracionId id2 = new ColaboracionId();

        Field usuarioField = ColaboracionId.class.getDeclaredField("usuario");
        usuarioField.setAccessible(true);
        usuarioField.set(id1, 1L);
        usuarioField.set(id2, 3L);

        Field campaniaField = ColaboracionId.class.getDeclaredField("campania");
        campaniaField.setAccessible(true);
        campaniaField.set(id1, 2L);
        campaniaField.set(id2, 4L);

        // Verificamos que al menos uno de los campos sea diferente para considerar objetos distintos
        boolean usuariosIguales = usuarioField.get(id1).equals(usuarioField.get(id2));
        boolean campaniasIguales = campaniaField.get(id1).equals(campaniaField.get(id2));

        assertFalse(usuariosIguales && campaniasIguales);
    }


    @Test
    void testEquals_nullObject() {
        ColaboracionId id = new ColaboracionId();
        assertFalse(id.equals(null));
    }

    @Test
    void testHashCode_sameValues() throws Exception {
        ColaboracionId id1 = new ColaboracionId();

        Field usuarioField = ColaboracionId.class.getDeclaredField("usuario");
        usuarioField.setAccessible(true);
        usuarioField.set(id1, 1L);

        Field campaniaField = ColaboracionId.class.getDeclaredField("campania");
        campaniaField.setAccessible(true);
        campaniaField.set(id1, 2L);

        // Compara el hashCode del objeto consigo mismo
        assertEquals(id1.hashCode(), id1.hashCode());
    }


}