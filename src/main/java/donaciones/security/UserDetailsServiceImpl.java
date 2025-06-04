package donaciones.security;

import donaciones.model.Usuario;
import donaciones.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // Marca esta clase como un componente de servicio de Spring
@RequiredArgsConstructor // Genera un constructor para inyectar UsuarioRepository
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository; // Necesitas tu repositorio para buscar usuarios

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Este método es llamado por Spring Security cuando necesita cargar los detalles de un usuario
        // durante el proceso de autenticación.

        // 1. Busca el usuario en tu base de datos por el email (que actúa como "username")
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> 
                new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        // 2. Si el usuario se encuentra, crea y devuelve una instancia de tu UserPrincipal
        // UserPrincipal ya implementa UserDetails, que es lo que Spring Security espera.
        return new UserPrincipal(usuario);
    }
}