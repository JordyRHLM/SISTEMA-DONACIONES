package donaciones.repository;

import donaciones.model.Organizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OrganizacionRepository extends JpaRepository<Organizacion, Long> {
    
    boolean existsByNombre(String nombre);

    Optional<Organizacion> findByNombre(String nombre);
}