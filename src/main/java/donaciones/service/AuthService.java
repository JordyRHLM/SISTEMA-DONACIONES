// donaciones.service.AuthService.java
package donaciones.service;

import donaciones.dto.request.LoginRequest;
import donaciones.dto.request.RegisterRequest;
import donaciones.dto.response.JwtResponse;
import donaciones.exception.ValidacionException;
import donaciones.model.Usuario;
import donaciones.repository.UsuarioRepository;
import donaciones.security.JwtTokenProvider;
import donaciones.security.UserPrincipal; // Asegúrate de que esta importación sea correcta
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // Sigue siendo útil
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public JwtResponse login(LoginRequest request) {
        // Este método está bien, usa el AuthenticationManager para la autenticación real
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword())
        );
        
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        
        String jwt = tokenProvider.generateToken(authentication);
        
        return JwtResponse.builder()
            .token(jwt)
            .id(userPrincipal.getId())
            .email(userPrincipal.getEmail())
            .name(userPrincipal.getNombre())
            .isAdmin(userPrincipal.getIsAdmin())
            .isOrgOwner(userPrincipal.getIsOrgOwner())
            .build();
    }

    public JwtResponse register(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new ValidacionException("El email ya está registrado");
        }

        // 1. Codificar la contraseña para guardar en la base de datos
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 2. Construir el objeto Usuario con la contraseña encriptada
        Usuario usuario = Usuario.builder()
            .nombre(request.getName())
            .email(request.getEmail())
            .password(encodedPassword) // Guarda la contraseña ENCRIPTADA
            .isOrgOwner(request.getIsOrgOwner() != null ? request.getIsOrgOwner() : false)
            .isAdmin(request.getIsAdmin() != null ? request.getIsAdmin() : false)// Aseguramos que is_admin sea false por defecto
            .build();

        // 3. Guardar el usuario en la base de datos
        usuarioRepository.save(usuario);

        // --- SOLUCIÓN PARA LA AUTENTICACIÓN DESPUÉS DEL REGISTRO ---
        // 4. Crear un UserPrincipal directamente del usuario recién guardado.
        //    ¡Este UserPrincipal ya tiene la contraseña encriptada y los roles!
        UserPrincipal userPrincipal = new UserPrincipal(usuario); 

        // 5. Crear un objeto Authentication. Como el usuario ya fue creado y validado,
        //    no necesitamos que AuthenticationManager lo "autentique" desde cero.
        //    Simplemente construimos un token que indica que ya está autenticado.
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            userPrincipal, // El principal (el usuario autenticado)
            null,          // Las credenciales (contraseña) son null porque ya está autenticado
            userPrincipal.getAuthorities() // Los roles/autoridades del usuario
        );

        // Opcional: Si quieres que el usuario esté autenticado en la sesión actual de Spring Security
        // después del registro (útil si hay llamadas subsiguientes que requieran autenticación en la misma request)
        // SecurityContextHolder.getContext().setAuthentication(authentication);

        // 6. Generar el token JWT usando el objeto Authentication recién creado
        String jwt = tokenProvider.generateToken(authentication);
        
        // 7. Retornar la respuesta JWT con los detalles del usuario
        return JwtResponse.builder()
            .token(jwt)
            .id(userPrincipal.getId())
            .email(userPrincipal.getEmail())
            .name(userPrincipal.getNombre())
            .isAdmin(userPrincipal.getIsAdmin())
            .isOrgOwner(userPrincipal.getIsOrgOwner())
            .build();
    }
}