package donaciones.controller;

import donaciones.dto.request.ColaboracionRequest;
import donaciones.dto.response.ColaboracionResponse;
import donaciones.service.ColaboracionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
// Import the ValidacionException class (update the package if needed)
import donaciones.exception.ValidacionException;

@RestController
@RequestMapping("/api/colaboraciones")
@Tag(name = "Colaboraciones-controller", description = "Operaciones CRUD para colaboraciones")
@RequiredArgsConstructor
public class ColaboracionController {

    private final ColaboracionService colaboracionService;

    @Operation(summary = "Crear nueva colaboración")
    @PostMapping
    public ResponseEntity<ColaboracionResponse> crearColaboracion(
            @Valid @RequestBody ColaboracionRequest request) {
        ColaboracionResponse response = colaboracionService.crearColaboracion(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener colaboraciones por campaña")
    @GetMapping("/campania/{campaniaId}")
    public ResponseEntity<List<ColaboracionResponse>> listarPorCampania(
            @PathVariable Long campaniaId) {
        List<ColaboracionResponse> response = colaboracionService.listarPorCampania(campaniaId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener colaboraciones por usuario")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ColaboracionResponse>> listarPorUsuario(
            @PathVariable Long usuarioId) {
        List<ColaboracionResponse> response = colaboracionService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar colaboración")
    @DeleteMapping
    public ResponseEntity<Void> eliminarColaboracion(
            @RequestParam Long usuarioId, 
            @RequestParam Long campaniaId) {
        colaboracionService.eliminarColaboracion(usuarioId, campaniaId);
        return ResponseEntity.noContent().build();
    }
    // En ColaboracionController.java
@ExceptionHandler(ValidacionException.class)
public ResponseEntity<String> handleValidacionException(ValidacionException ex) {
    return ResponseEntity.badRequest().body(ex.getMessage());
}
}