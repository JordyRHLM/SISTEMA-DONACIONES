package donaciones.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RecursoNoEncontradoExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String message = "Resource not found";
        RecursoNoEncontradoException exception = new RecursoNoEncontradoException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testExceptionType() {
        assertThrows(RecursoNoEncontradoException.class, () -> {
            throw new RecursoNoEncontradoException("Test exception");
        });
    }

    @Test
    void testDefaultMessage() {
        RecursoNoEncontradoException exception = new RecursoNoEncontradoException("Default message");
        assertEquals("Default message", exception.getMessage());
    }

    @Test
    void testEmptyMessage() {
        RecursoNoEncontradoException exception = new RecursoNoEncontradoException("");
        assertEquals("", exception.getMessage());
    }
}