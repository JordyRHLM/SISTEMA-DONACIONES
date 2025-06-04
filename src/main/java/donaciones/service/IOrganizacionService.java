package donaciones.service;

import donaciones.dto.request.OrganizacionRequest;
import donaciones.dto.response.OrganizacionResponse;

import java.util.List;

public interface IOrganizacionService {

    List<OrganizacionResponse> listar();

    OrganizacionResponse obtenerPorId(Long id);

    OrganizacionResponse crear(OrganizacionRequest request);

    OrganizacionResponse actualizar(Long id, OrganizacionRequest request);

    void eliminar(Long id);

    OrganizacionResponse darBaja(Long id); // cambia a INACTIVA

    OrganizacionResponse activar(Long id); // cambia a ACTIVA
}