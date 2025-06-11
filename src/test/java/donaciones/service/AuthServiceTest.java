package donaciones.service;

import donaciones.dto.request.LoginRequest;
import donaciones.dto.request.RegisterRequest;
import donaciones.dto.response.JwtResponse;
import donaciones.exception.ValidacionException;
import donaciones.model.Usuario;
import donaciones.repository.UsuarioRepository;
import donaciones.security.JwtTokenProvider;
import donaciones.security.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenProvider tokenProvider;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_success() {
        LoginRequest request = new LoginRequest();
        request.setEmail("admin1@gmail.com");
        request.setPassword("password");

        UserPrincipal userPrincipal = new UserPrincipal(Usuario.builder()
            .id(1L)
            .email("admin1@gmail.com")
            .nombre("Admin User")
            .isAdmin(true)
            .isOrgOwner(false)
            .password("encodedPassword")
            .build());

        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any()))
            .thenReturn(authentication);

        when(authentication.getPrincipal()).thenReturn(userPrincipal);

        when(tokenProvider.generateToken(authentication)).thenReturn("testToken");

        JwtResponse response = authService.login(request);

        assertEquals("testToken", response.getToken());
        assertEquals("admin1@gmail.com", response.getEmail());
    }


    @Test
    void register_success() {
        RegisterRequest request = new RegisterRequest();
        request.setName("Test User");
        request.setEmail("test@example.com");
        request.setPassword("password");
        request.setIsOrgOwner(false);
        request.setIsAdmin(false);

        when(usuarioRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(usuarioRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(tokenProvider.generateToken(any())).thenReturn("testToken");

        JwtResponse response = authService.register(request);

        assertEquals("testToken", response.getToken());
        assertEquals("test@example.com", response.getEmail());
    }


    @Test
    void register_emailExists() {
        RegisterRequest request = new RegisterRequest();
        request.setName("Test User");
        request.setEmail("test@example.com");
        request.setPassword("password");
        request.setIsOrgOwner(false);
        request.setIsAdmin(false);

        when(usuarioRepository.existsByEmail(request.getEmail())).thenReturn(true);

        assertThrows(ValidacionException.class, () -> authService.register(request));
    }


    @Test
    void register_defaultAdminAndOrgOwnerFalse() {
        RegisterRequest request = new RegisterRequest();
        request.setName("Test User");
        request.setEmail("test@example.com");
        request.setPassword("password");
        request.setIsAdmin(null);
        request.setIsOrgOwner(null);

        when(usuarioRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(usuarioRepository.save(any())).thenAnswer(invocation -> {
            Usuario usuario = invocation.getArgument(0);
            assertEquals(false, usuario.getIsAdmin());
            assertEquals(false, usuario.getIsOrgOwner());
            return usuario;
        });
        when(tokenProvider.generateToken(any())).thenReturn("testToken");

        authService.register(request);
    }

}