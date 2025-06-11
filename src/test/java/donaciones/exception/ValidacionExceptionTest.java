package donaciones.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidacionExceptionTest {

    @Test
    void testValidacionExceptionCreation() {
        ValidacionException exception = new ValidacionException("Test message");
        assertNotNull(exception);
    }

    @Test
    void testValidacionExceptionMessage() {
        String message = "Another test message";
        ValidacionException exception = new ValidacionException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testValidacionExceptionInheritance() {
        ValidacionException exception = new ValidacionException("Inheritance test");
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testEmptyMessage() {
        ValidacionException exception = new ValidacionException("");
        assertEquals("", exception.getMessage());
    }

    @Test
    void testNullMessage() {
        ValidacionException exception = new ValidacionException(null);
        assertNull(exception.getMessage());
    }
}