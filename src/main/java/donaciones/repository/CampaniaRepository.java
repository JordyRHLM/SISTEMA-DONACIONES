package donaciones.repository;

import donaciones.model.Campania;
import donaciones.model.enums.CampaniaEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaniaRepository extends JpaRepository<Campania, Long> {
    List<Campania> findByOrganizacionId(Long organizacionId);
    List<Campania> findByEstado(CampaniaEstado estado);
}