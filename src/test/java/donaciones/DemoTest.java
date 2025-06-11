// filepath: src/test/java/donaciones/DemoTest.java
package donaciones;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DemoTest {
    @Test
    void junitWorks() {
        int resultado = 3 + 1;
        System.out.println("El resultado de la suma es: " + resultado);
        System.out.println("JUNIT funciona correctamente");
        assertEquals(4, resultado);
    }

    @Test
    void mockitoWorks() {
        Runnable mock = mock(Runnable.class);
        mock.run();
        verify(mock).run();
        System.out.println("MOCKITO funcionó correctamente");
    }
}

//comando para verificar que el test funciona: mvn test -Dtest=DemoTest