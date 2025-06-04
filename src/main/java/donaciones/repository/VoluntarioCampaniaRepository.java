package donaciones.repository;

import donaciones.model.VoluntarioCampania;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoluntarioCampaniaRepository extends JpaRepository<VoluntarioCampania, Long> {
    List<VoluntarioCampania> findByCampaniaId(Long campaniaId);
    List<VoluntarioCampania> findByUsuarioId(Long usuarioId);
    boolean existsByUsuarioIdAndCampaniaId(Long usuarioId, Long campaniaId);
}