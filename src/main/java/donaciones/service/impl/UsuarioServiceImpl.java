package donaciones.service.impl;

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