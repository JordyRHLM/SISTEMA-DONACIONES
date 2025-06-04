package donaciones.validation;

import donaciones.dto.UsuarioDTO;
import donaciones.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioValidator {

    private final UsuarioRepository usuarioRepository;

    public boolean emailYaExiste(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}