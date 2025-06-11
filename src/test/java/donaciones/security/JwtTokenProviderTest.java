package donaciones.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    private Authentication authentication;
    private UserPrincipal userPrincipal;

    @BeforeEach
    void setUp() throws Exception {
        jwtTokenProvider = new JwtTokenProvider();

        // Inyectar valor a jwtSecret por reflexión
        Field secretField = JwtTokenProvider.class.getDeclaredField("jwtSecret");
        secretField.setAccessible(true);
        secretField.set(jwtTokenProvider, "c2VjcmV0LWtleS1mb3ItdG9rZW4tZ2VuZXJhdGlvbi10ZXN0"); // Base64 encoded "secret-key"

        // Inyectar valor a jwtExpirationInMs por reflexión
        Field expirationField = JwtTokenProvider.class.getDeclaredField("jwtExpirationInMs");
        expirationField.setAccessible(true);
        expirationField.set(jwtTokenProvider, 3600000); // 1 hour

        authentication = Mockito.mock(Authentication.class);
        userPrincipal = Mockito.mock(UserPrincipal.class);

        when(authentication.getPrincipal()).thenReturn(userPrincipal);
        when(userPrincipal.getId()).thenReturn(1L);
    }


    @Test
    void generateToken() {
        String token = jwtTokenProvider.generateToken(authentication);
        assertNotNull(token);
    }


    @Test
    void getUserIdFromJWT() {
        String token = jwtTokenProvider.generateToken(authentication);
        Long userId = jwtTokenProvider.getUserIdFromJWT(token);
        assertEquals(1L, userId);
    }

    @Test
    void validateToken_validToken() {
        String token = jwtTokenProvider.generateToken(authentication);
        assertTrue(jwtTokenProvider.validateToken(token));
    }

    @Test
    void validateToken_invalidToken() {
        assertFalse(jwtTokenProvider.validateToken("invalid-token"));
    }
}