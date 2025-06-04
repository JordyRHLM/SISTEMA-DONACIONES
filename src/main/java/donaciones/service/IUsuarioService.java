package donaciones.service;

import donaciones.dto.response.UsuarioResponse;
import donaciones.model.Usuario;

import java.util.List;

public interface IUsuarioService {
    UsuarioResponse getUsuarioById(Long id);
    UsuarioResponse getCurrentUsuario();
    List<UsuarioResponse> getAllUsuarios();
    void deleteUsuario(Long id);
    Usuario getUsuarioEntity(Long id);
}