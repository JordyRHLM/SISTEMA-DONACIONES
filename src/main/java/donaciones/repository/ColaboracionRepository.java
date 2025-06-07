package donaciones.repository;


import donaciones.model.Colaboracion;
import donaciones.model.ColaboracionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColaboracionRepository extends JpaRepository<Colaboracion, ColaboracionId> {
    
    // Método para buscar colaboraciones por ID de campaña
    List<Colaboracion> findByCampaniaId(Long campaniaId);
    
    // Método para buscar colaboraciones por ID de usuario
    List<Colaboracion> findByUsuarioId(Long usuarioId);
    
    // Método para eliminar colaboración por IDs de usuario y campaña
    void deleteByUsuarioIdAndCampaniaId(Long usuarioId, Long campaniaId);
    
    // Método para verificar si existe una colaboración
    boolean existsByUsuarioIdAndCampaniaId(Long usuarioId, Long campaniaId);
}