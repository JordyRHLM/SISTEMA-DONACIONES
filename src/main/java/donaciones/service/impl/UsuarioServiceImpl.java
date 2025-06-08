package donaciones.service.impl;

import donaciones.dto.request.RegisterRequest;
import donaciones.dto.response.UsuarioResponse;
import donaciones.exception.RecursoNoEncontradoException;
import donaciones.model.Usuario;
import donaciones.repository.UsuarioRepository;
import donaciones.security.UserPrincipal;
import donaciones.service.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UsuarioResponse getUsuarioById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));
        return mapToResponse(usuario);
    }

    @Override
    public UsuarioResponse getCurrentUsuario() {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return getUsuarioById(currentUser.getId());
    }

    @Override
    public List<UsuarioResponse> getAllUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));
        usuarioRepository.delete(usuario);
    }

    @Override
    public Usuario getUsuarioEntity(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));
    }

    @Override
    public UsuarioResponse createUsuario(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getName());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(request.getPassword()); // Usa passwordEncoder si es necesario
        usuario.setIsAdmin(request.getIsAdmin() != null ? request.getIsAdmin() : false);
        usuario.setIsOrgOwner(request.getIsOrgOwner() != null ? request.getIsOrgOwner() : false);
        usuarioRepository.save(usuario);
        return mapToResponse(usuario);
    }

    @Override
    public UsuarioResponse updateUsuario(Long id, RegisterRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        // Actualiza los campos necesarios
        usuario.setNombre(request.getName());
        usuario.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            // Aquí deberías inyectar y usar tu passwordEncoder si quieres actualizar la contraseña
            // usuario.setPassword(passwordEncoder.encode(request.getPassword()));
            usuario.setPassword(request.getPassword()); // Solo para ejemplo, mejor encripta la contraseña
        }
        usuario.setIsAdmin(request.getIsAdmin() != null ? request.getIsAdmin() : usuario.getIsAdmin());
        usuario.setIsOrgOwner(request.getIsOrgOwner() != null ? request.getIsOrgOwner() : usuario.getIsOrgOwner());

        usuarioRepository.save(usuario);

        return mapToResponse(usuario);
    }

    private UsuarioResponse mapToResponse(Usuario usuario) {
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .isAdmin(usuario.getIsAdmin())
                .isOrgOwner(usuario.getIsOrgOwner())
                .createdAt(usuario.getCreatedAt())
                .build();
    }
}