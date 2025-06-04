package donaciones.service;

import java.util.List;
import donaciones.dto.OrganizacionDTO;

public interface IOrganizacionService{

    List<OrganizacionDTO> listarOrganizaciones();
    
    OrganizacionDTO obtenerOrganizacionPorId(Long id_org);
    
    OrganizacionDTO crearOrganizacion(OrganizacionDTO organizacionDTO);
    
    OrganizacionDTO actualizarOrganizacion(Long id_org, OrganizacionDTO organizacionDTO);
    
    OrganizacionDTO eliminarOrganizacion(Long id_org);

    List<OrganizacionDTO> buscarOrganizacionesPorNombre(String nombre);
}