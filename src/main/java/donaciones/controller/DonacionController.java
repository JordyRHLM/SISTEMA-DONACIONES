package donaciones.controller;

import donaciones.dto.request.DonacionRequest;
import donaciones.dto.response.DonacionResponse;
import donaciones.service.DonacionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donaciones")
public class DonacionController {

    private final DonacionService donacionService;

    public DonacionController(DonacionService donacionService) {
        this.donacionService = donacionService;
    }

    // Listar todas las donaciones
    @GetMapping
    public ResponseEntity<List<DonacionResponse>> listarTodasLasDonaciones() {
        List<DonacionResponse> donaciones = donacionService.listarTodasLasDonaciones();
        return ResponseEntity.ok(donaciones);
    }

    // Obtener donación por ID
    @GetMapping("/{id}")
    public ResponseEntity<DonacionResponse> obtenerPorId(@PathVariable Long id) {
        DonacionResponse response = donacionService.obtenerDonacionPorId(id);
        return ResponseEntity.ok(response);
    }

    // Crear una nueva donación
    @PostMapping
    public ResponseEntity<DonacionResponse> crearDonacion(@Valid @RequestBody DonacionRequest request) {
        DonacionResponse response = donacionService.crearDonacion(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Editar donación por ID
    @PutMapping("/{id}/editar")
    public ResponseEntity<DonacionResponse> editarDonacionPorId(
            @PathVariable Long id,
            @RequestParam Long organizacionId,
            @Valid @RequestBody DonacionRequest request) {
        DonacionResponse response = donacionService.editarDonacionPorId(id, organizacionId, request);
        return ResponseEntity.ok(response);
    }

    // Actualizar estado de donación
    @PatchMapping("/{id}/estado")
    public ResponseEntity<DonacionResponse> actualizarEstado(
            @PathVariable Long id,
            @RequestParam String estado) {
        DonacionResponse response = donacionService.actualizarEstado(id, estado);
        return ResponseEntity.ok(response);
    }

    // Eliminar donación por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDonacionPorId(@PathVariable Long id) {
        donacionService.eliminarDonacionPorId(id);
        return ResponseEntity.noContent().build();
    }

    // Listar donaciones por usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<DonacionResponse>> listarPorUsuario(@PathVariable Long usuarioId) {
        List<DonacionResponse> donaciones = donacionService.listarDonacionesPorUsuario(usuarioId);
        return ResponseEntity.ok(donaciones);
    }

    // Listar donaciones por campaña
    @GetMapping("/campania/{campaniaId}")
    public ResponseEntity<List<DonacionResponse>> listarPorCampania(@PathVariable Long campaniaId) {
        List<DonacionResponse> donaciones = donacionService.listarDonacionesPorCampania(campaniaId);
        return ResponseEntity.ok(donaciones);
    }

    // Listar donaciones por organización (con filtro opcional por estado)
    @GetMapping("/organizacion/{organizacionId}")
    public ResponseEntity<List<DonacionResponse>> listarPorOrganizacion(
            @PathVariable Long organizacionId,
            @RequestParam(required = false) String estado) {
        List<DonacionResponse> donaciones = donacionService.listarDonacionesPorOrganizacion(organizacionId, estado);
        return ResponseEntity.ok(donaciones);
    }

    // Asignar campaña a donación
    @GetMapping("/asignar-campania")
    public ResponseEntity<List<DonacionResponse>> asignarCampaniaADonacion(
            @RequestParam Long campaniaId,
            @RequestParam Long organizacionId) {
        List<DonacionResponse> donaciones = donacionService.asignarCampaniaADonacion(campaniaId, organizacionId);
        return ResponseEntity.ok(donaciones);
    }
}