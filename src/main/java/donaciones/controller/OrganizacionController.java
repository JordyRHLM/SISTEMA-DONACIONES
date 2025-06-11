package donaciones.controller;

import donaciones.dto.request.OrganizacionRequest;
import donaciones.dto.response.OrganizacionResponse;
import donaciones.service.IOrganizacionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/organizaciones")
@RequiredArgsConstructor
public class OrganizacionController {

    private final IOrganizacionService organizacionService;

    //  Listar todas las organizaciones
    @GetMapping
    public ResponseEntity<List<OrganizacionResponse>> listarOrganizaciones() {
        return ResponseEntity.ok(organizacionService.listar());
    }

    //  Obtener una organización por ID
    @GetMapping("/{id}")
    public ResponseEntity<OrganizacionResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(organizacionService.obtenerPorId(id));
    }

    //  Crear nueva organización
    @PostMapping
    public ResponseEntity<OrganizacionResponse> crearOrganizacion(
            @Valid @RequestBody OrganizacionRequest request) {
        return ResponseEntity.ok(organizacionService.crear(request));
    }

    //  Actualizar organización
    @PutMapping("/{id}")
    public ResponseEntity<OrganizacionResponse> actualizarOrganizacion(
            @PathVariable Long id,
            @Valid @RequestBody OrganizacionRequest request) {
        return ResponseEntity.ok(organizacionService.actualizar(id, request));
    }

    //  Eliminar organización (borrado físico)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOrganizacion(@PathVariable Long id) {
        organizacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    //  Dar de baja (cambiar estado a INACTIVA)
    @PatchMapping("/{id}/baja")
    public ResponseEntity<OrganizacionResponse> darBaja(@PathVariable Long id) {
        return ResponseEntity.ok(organizacionService.darBaja(id));
    }

    //  Activar organización (cambiar estado a ACTIVA)
    @PatchMapping("/{id}/activar")
    public ResponseEntity<OrganizacionResponse> activar(@PathVariable Long id) {
        return ResponseEntity.ok(organizacionService.activar(id));
    }

    // obtener el id del dueño de la organización, no su nombre, id es long
    @GetMapping("/{id}/dueno")
    public ResponseEntity<Long> obtenerDueno(@PathVariable Long id) {
        return ResponseEntity.ok(organizacionService.obtenerDueno(id));
    }
    

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<OrganizacionResponse>> listarPorOwner(@PathVariable Long ownerId) {
        return ResponseEntity.ok(organizacionService.listarPorOwner(ownerId));
    }

    // obtencion de campañas por organización
    @GetMapping("/{id}/campanas")
    public ResponseEntity<List<?>> obtenerCampanasPorOrganizacion(@PathVariable Long id) {
        return ResponseEntity.ok(organizacionService.obtenerCampanasPorOrganizacion(id));
    }

}