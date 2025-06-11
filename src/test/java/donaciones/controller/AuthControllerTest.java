package donaciones.controller;

import donaciones.dto.request.LoginRequest;
import donaciones.dto.request.RegisterRequest;
import donaciones.dto.response.JwtResponse;
import donaciones.security.TokenBlacklist;
import donaciones.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @Mock
    private TokenBlacklist tokenBlacklist;

    @InjectMocks
    private AuthController authController;

    @Test
    void login_validRequest_returnsOkResponse() {
        LoginRequest request = new LoginRequest();
        request.setEmail("email");
        request.setPassword("password");

        JwtResponse jwtResponse = JwtResponse.builder()
                .token("token")
                .build();

        when(authService.login(request)).thenReturn(jwtResponse);

        ResponseEntity<JwtResponse> response = authController.login(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(jwtResponse, response.getBody());
    }


    @Test
    void register_validRequest_returnsOkResponse() {
    RegisterRequest request = new RegisterRequest();
    request.setName("name");
    request.setEmail("email");
    request.setPassword("password");
    request.setIsOrgOwner(false);
    request.setIsAdmin(false);

    JwtResponse jwtResponse = JwtResponse.builder()
            .token("token")
            .build();

    when(authService.register(request)).thenReturn(jwtResponse);

    ResponseEntity<JwtResponse> response = authController.register(request);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(jwtResponse, response.getBody());
}


    @Test
    void logout_validToken_blacklistsTokenAndReturnsOk() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        String token = "testToken";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        ResponseEntity<?> response = authController.logout(request);

        verify(tokenBlacklist).blacklistToken(token);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Logout exitoso. El token ha sido invalidado.", response.getBody());
    }

    @Test
    void logout_noToken_returnsOk() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn(null);

        ResponseEntity<?> response = authController.logout(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Logout exitoso. El token ha sido invalidado.", response.getBody());
    }
}