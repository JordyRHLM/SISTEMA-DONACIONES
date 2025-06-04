package donaciones.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import donaciones.model.Organizacion;

public interface OrganizacionRepository extends JpaRepository<Organizacion, Long>{
    boolean existsByEmailContacto(String emailContacto);

    boolean existsByNombre(String nombre);
    boolean existsByTelefono(String telefono);
    
}