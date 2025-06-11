package donaciones.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtResponseTest {

    @Test
    void testNoArgsConstructor() {
        JwtResponse response = new JwtResponse();
        assertNotNull(response);
    }

    @Test
    void testAllArgsConstructor() {
        JwtResponse response = new JwtResponse("testToken", 1L, "testName", "test@example.com", true, false);
        assertEquals("testToken", response.getToken());
        assertEquals(1L, response.getId());
        assertEquals("testName", response.getName());
        assertEquals("test@example.com", response.getEmail());
        assertTrue(response.getIsAdmin());
        assertFalse(response.getIsOrgOwner());
    }

    @Test
    void testBuilder() {
        JwtResponse response = JwtResponse.builder()
                .token("testToken")
                .id(1L)
                .name("testName")
                .email("test@example.com")
                .isAdmin(true)
                .isOrgOwner(false)
                .build();

        assertEquals("testToken", response.getToken());
        assertEquals(1L, response.getId());
        assertEquals("testName", response.getName());
        assertEquals("test@example.com", response.getEmail());
        assertTrue(response.getIsAdmin());
        assertFalse(response.getIsOrgOwner());
    }

    @Test
    void testSettersAndGetters() {
        JwtResponse response = new JwtResponse();
        response.setToken("newToken");
        response.setId(2L);
        response.setName("newName");
        response.setEmail("new@example.com");
        response.setIsAdmin(false);
        response.setIsOrgOwner(true);

        assertEquals("newToken", response.getToken());
        assertEquals(2L, response.getId());
        assertEquals("newName", response.getName());
        assertEquals("new@example.com", response.getEmail());
        assertFalse(response.getIsAdmin());
        assertTrue(response.getIsOrgOwner());
    }

    @Test
    void testEqualsAndHashCode() {
        JwtResponse response1 = JwtResponse.builder().token("token1").id(1L).name("name1").email("email1").isAdmin(true).isOrgOwner(false).build();
        JwtResponse response2 = JwtResponse.builder().token("token1").id(1L).name("name1").email("email1").isAdmin(true).isOrgOwner(false).build();
        JwtResponse response3 = JwtResponse.builder().token("token2").id(2L).name("name2").email("email2").isAdmin(false).isOrgOwner(true).build();

        assertEquals(response1, response2);
        assertNotEquals(response1, response3);
        assertEquals(response1.hashCode(), response2.hashCode());
        assertNotEquals(response1.hashCode(), response3.hashCode());
    }
}