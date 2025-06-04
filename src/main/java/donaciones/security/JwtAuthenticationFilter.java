package donaciones.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor; // Importa lombok.RequiredArgsConstructor
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails; // Posiblemente UserPrincipal implemente UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component; // ¡IMPORTANTE! Agrega esta anotación
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import donaciones.repository.UsuarioRepository;
import donaciones.model.Usuario; // Asegúrate de importar tu clase Usuario

@Component // Esta anotación le dice a Spring que esta clase es un componente gestionado
@RequiredArgsConstructor // Genera automáticamente el constructor con los campos 'final'
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final UsuarioRepository usuarioRepository;
    // No necesitas escribir el constructor manualmente si usas @RequiredArgsConstructor
    /*
    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, UsuarioRepository usuarioRepository) {
        this.tokenProvider = tokenProvider;
        this.usuarioRepository = usuarioRepository;
    }
    */

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            // Verifica si el JWT existe y es válido
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Long userId = tokenProvider.getUserIdFromJWT(jwt);

                // Carga los detalles del usuario y establece la autenticación
                usuarioRepository.findById(userId)
                    .ifPresent(usuario -> {
                        // UserPrincipal debe implementar UserDetails o ser compatible
                        // Si UserPrincipal es tu implementación de UserDetails, está bien.
                        // Si no tienes UserPrincipal, podrías usar directamente el objeto Usuario
                        // o una clase que implemente UserDetails (por ejemplo, UserDetailsImpl)
                        UserDetails userDetails = new UserPrincipal(usuario); // Asumiendo UserPrincipal implementa UserDetails
                        
                        // Crea el token de autenticación
                        UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        
                        // Establece los detalles de la solicitud en el token
                        authentication.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));
                        
                        // Establece la autenticación en el SecurityContext
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    });
            }
        } catch (Exception ex) {
            // Es buena práctica loggear el error pero también puedes decidir cómo quieres manejarlo
            // Por ejemplo, enviar una respuesta de error al cliente.
            logger.error("Could not set user authentication in security context", ex);
        }

        // Continúa con la cadena de filtros
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        // Verifica si el encabezado Authorization contiene un token Bearer
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Extrae el token sin "Bearer "
        }
        return null;
    }
}