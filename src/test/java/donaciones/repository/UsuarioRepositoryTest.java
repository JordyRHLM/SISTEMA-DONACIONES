package donaciones.repository;

import donaciones.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class UsuarioRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void whenFindByEmail_thenReturnUsuario() {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@example.com");
        usuario.setNombre("Test User");
        usuario.setPassword("testpassword"); // Asignación del campo obligatorio

        entityManager.persist(usuario);
        entityManager.flush();

        Optional<Usuario> found = usuarioRepository.findByEmail(usuario.getEmail());

        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getEmail()).isEqualTo(usuario.getEmail());
    }


    @Test
    public void whenFindByEmail_thenReturnEmptyOptional() {
        Optional<Usuario> found = usuarioRepository.findByEmail("nonexistent@example.com");
        assertThat(found.isPresent()).isFalse();
    }

    @Test
    public void whenExistsByEmail_thenReturnTrue() {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@example.com");
        usuario.setNombre("Test User");
        usuario.setPassword("testpassword"); // ¡Asignar password obligatorio!

        entityManager.persist(usuario);
        entityManager.flush();

        boolean exists = usuarioRepository.existsByEmail(usuario.getEmail());
        assertThat(exists).isTrue();
    }


    @Test
    public void whenExistsByEmail_thenReturnFalse() {
        boolean exists = usuarioRepository.existsByEmail("nonexistent@example.com");
        assertThat(exists).isFalse();
    }

    @Test
    public void whenSaveUsuario_thenSuccess() {
        Usuario usuario = new Usuario();
        usuario.setEmail("new@example.com");
        usuario.setNombre("New User");
        usuario.setPassword("testpassword"); // <- ¡Importante!

        Usuario savedUsuario = usuarioRepository.save(usuario);

        assertThat(savedUsuario.getId()).isNotNull();
        assertThat(savedUsuario.getEmail()).isEqualTo("new@example.com");
    }

}