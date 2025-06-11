package donaciones.repository;


import donaciones.model.Colaboracion;
import donaciones.model.ColaboracionId;
import donaciones.model.enums.RolColaboracion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
    Optional<Colaboracion> findByUsuarioIdAndCampaniaId(Long usuarioId, Long campaniaId);
     List<Colaboracion> findAll();
    
    // Método para actualizar el rol de una colaboración
    @Modifying
    @Query("UPDATE Colaboracion c SET c.rol = :rol WHERE c.usuario.id = :usuarioId AND c.campania.id = :campaniaId")
    void actualizarRol(@Param("usuarioId") Long usuarioId, 
                      @Param("campaniaId") Long campaniaId, 
                      @Param("rol") RolColaboracion rol);
    //dashboard
    long count();
 }