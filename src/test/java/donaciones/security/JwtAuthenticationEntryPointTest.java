package donaciones.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.doThrow;
import org.springframework.security.core.AuthenticationException;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import static org.mockito.Mockito.verify;


class JwtAuthenticationEntryPointTest {

    private JwtAuthenticationEntryPoint entryPoint;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private AuthenticationException authException;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        entryPoint = new JwtAuthenticationEntryPoint();
    }

    @Test
    void commence_sendsUnauthorizedError() throws IOException, ServletException {
        entryPoint.commence(request, response, authException);
        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "No autorizado");
    }

    
    @Test
    void commence_handlesIOException() throws IOException, ServletException {
        doThrow(new IOException("Test IOException"))
            .when(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "No autorizado");

        assertThrows(IOException.class, () -> entryPoint.commence(request, response, authException));
    }

    @Test
    void commence_handlesServletException() throws IOException, ServletException {
    doThrow(new IOException("Test IOException"))
        .when(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "No autorizado");

    assertThrows(IOException.class, () -> entryPoint.commence(request, response, authException));
}


    @Test
    void commence_withDifferentErrorMessage() throws IOException, ServletException {
        JwtAuthenticationEntryPoint customEntryPoint = new JwtAuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request,
                                 HttpServletResponse response,
                                 AuthenticationException authException) throws IOException, ServletException {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Custom Error");
            }
        };

        customEntryPoint.commence(request, response, authException);
        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Custom Error");
    }
}