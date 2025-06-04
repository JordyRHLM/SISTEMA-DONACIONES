package donaciones.repository;

import donaciones.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);

    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
    List<Usuario> findAllByRol(String rol);
}