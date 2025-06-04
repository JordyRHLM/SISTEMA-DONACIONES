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
}