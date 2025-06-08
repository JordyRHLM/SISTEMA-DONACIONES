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

    // Crear una nueva donación
    @PostMapping
    public ResponseEntity<DonacionResponse> crearDonacion(@Valid @RequestBody DonacionRequest request) {
        DonacionResponse response = donacionService.crearDonacion(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<DonacionResponse> actualizarEstado(
            @PathVariable Long id,
            @RequestParam String estado) {
        DonacionResponse response = donacionService.actualizarEstado(id, estado);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<DonacionResponse>> listarPorUsuario(@PathVariable Long usuarioId) {
        List<DonacionResponse> donaciones = donacionService.listarDonacionesPorUsuario(usuarioId);
        return ResponseEntity.ok(donaciones);
    }

    @GetMapping("/campania/{campaniaId}")
    public ResponseEntity<List<DonacionResponse>> listarPorCampania(@PathVariable Long campaniaId) {
        List<DonacionResponse> donaciones = donacionService.listarDonacionesPorCampania(campaniaId);
        return ResponseEntity.ok(donaciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonacionResponse> obtenerPorId(@PathVariable Long id) {
        DonacionResponse response = donacionService.obtenerDonacionPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/organizacion/{organizacionId}")
    public ResponseEntity<List<DonacionResponse>> listarPorOrganizacion(
            @PathVariable Long organizacionId,
            @RequestParam(required = false) String estado) {
        List<DonacionResponse> donaciones = donacionService.listarDonacionesPorOrganizacion(organizacionId, estado);
        return ResponseEntity.ok(donaciones);
    }
    @PutMapping("/{id}/editar")
    public ResponseEntity<DonacionResponse> editarDonacionPorId(
            @PathVariable Long id,
            @RequestParam Long organizacionId,
            @Valid @RequestBody DonacionRequest request) {
        DonacionResponse response = donacionService.editarDonacionPorId(id, organizacionId, request);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/asignar-campania")
    public ResponseEntity<List<DonacionResponse>> asignarCampaniaADonacion(
            @RequestParam Long campaniaId,
            @RequestParam Long organizacionId) {
        List<DonacionResponse> donaciones = donacionService.asignarCampaniaADonacion(campaniaId, organizacionId);
        return ResponseEntity.ok(donaciones);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDonacionPorId(@PathVariable Long id) {
        donacionService.eliminarDonacionPorId(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/todas")
    public ResponseEntity<List<DonacionResponse>> listarTodasLasDonaciones() {
        List<DonacionResponse> donaciones = donacionService.listarTodasLasDonaciones();
        return ResponseEntity.ok(donaciones);
    }
}