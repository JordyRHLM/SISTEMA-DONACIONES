package donaciones.repository;

import donaciones.model.Organizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrganizacionRepository extends JpaRepository<Organizacion, Long> {
    
    boolean existsByNombre(String nombre);

    Optional<Organizacion> findByNombre(String nombre);

    @Query("SELECT o.id FROM Organizacion o WHERE o.owner.id = :ownerId")
    Optional<Long> findIdByOwnerId(@Param("ownerId") Long ownerId);

    List<Organizacion> findByOwnerId(Long ownerId);

}