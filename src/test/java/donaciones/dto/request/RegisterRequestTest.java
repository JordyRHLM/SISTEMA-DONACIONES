package donaciones.dto.request;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegisterRequestTest {

    @Test
    void testRegisterRequestCreation() {
        RegisterRequest request = new RegisterRequest();
        assertNotNull(request);
    }

    @Test
    void testSetName() {
        RegisterRequest request = new RegisterRequest();
        request.setName("Test Name");
        assertEquals("Test Name", request.getName());
    }

    @Test
    void testSetEmail() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        assertEquals("test@example.com", request.getEmail());
    }

    @Test
    void testSetPassword() {
        RegisterRequest request = new RegisterRequest();
        request.setPassword("password");
        assertEquals("password", request.getPassword());
    }

    @Test
    void testSetIsOrgOwner() {
        RegisterRequest request = new RegisterRequest();
        request.setIsOrgOwner(true);
        assertTrue(request.getIsOrgOwner());
    }
}