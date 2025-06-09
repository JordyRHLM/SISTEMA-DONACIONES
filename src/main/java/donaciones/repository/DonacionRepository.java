package donaciones.repository;

import donaciones.model.Donacion;
import donaciones.model.enums.DonacionEstado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonacionRepository extends JpaRepository<Donacion, Long> {
    List<Donacion> findByUsuarioId(Long usuarioId);
    List<Donacion> findByCampaniaId(Long campaniaId);
    List<Donacion> findByOrganizacionId(Long organizacionId);
    //listar por organizacion
    List<Donacion> findByOrganizacionIdAndEstado(Long organizacionId, DonacionEstado estado);
    List<Donacion> findByOrganizacionIdAndEstado(Long organizacionId, String estado);
    //editar donacion por id
    Donacion findByIdAndOrganizacionId(Long id, Long organizacionId);
    //asignar campania a donacion
    List<Donacion> findByCampaniaIdAndOrganizacionId(Long campaniaId, Long organizacionId);
    //eliminar donacion por id
    void deleteById(Long id);

    //listar todas las donaciones
    List<Donacion> findAll();

    //crear donacion
    Donacion save(Donacion donacion);

    @Query("SELECT d FROM Donacion d WHERE d.campania.id = :campaniaId AND d.estado IN :estados")
    List<Donacion> findByCampaniaIdAndEstadoIn(@Param("campaniaId") Long campaniaId,
                                               @Param("estados") List<DonacionEstado> estados);

}