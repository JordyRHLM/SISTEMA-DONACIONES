package donaciones.controller;

import donaciones.dto.request.CampaniaRequest;
import donaciones.dto.response.CampaniaRecaudacionResponse;
import donaciones.dto.response.CampaniaResponse;
import donaciones.service.CampaniaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campanias")
public class CampaniaController {

    private final CampaniaService campaniaService;

    public CampaniaController(CampaniaService campaniaService) {
        this.campaniaService = campaniaService;
    }

    @GetMapping
    public ResponseEntity<List<CampaniaResponse>> obtenerTodasLasCampanias() {
        List<CampaniaResponse> responses = campaniaService.obtenerTodas();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampaniaResponse> obtenerCampaniaPorId(@PathVariable Long id) {
        CampaniaResponse response = campaniaService.obtenerPorId(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CampaniaResponse> crearCampania(@RequestBody CampaniaRequest campaniaRequest) {
        CampaniaResponse response = campaniaService.crearCampania(campaniaRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CampaniaResponse> actualizarCampania(@PathVariable Long id, @RequestBody CampaniaRequest campaniaRequest) {
        CampaniaResponse response = campaniaService.actualizarCampania(id, campaniaRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCampania(@PathVariable Long id) {
        campaniaService.eliminarCampania(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/recaudacion")
    public ResponseEntity<CampaniaRecaudacionResponse> obtenerRecaudacion(@PathVariable Long id) {
        CampaniaRecaudacionResponse response = campaniaService.calcularRecaudacion(id);
        return ResponseEntity.ok(response);
    }
}