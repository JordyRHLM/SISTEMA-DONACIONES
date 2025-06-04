package donaciones.controller;

import donaciones.dto.CampaniaDTO;
import donaciones.service.ICampaniaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/campanias")
public class CampaniaController {

    private final ICampaniaService campaniaService;

    @Autowired
    public CampaniaController(ICampaniaService campaniaService) {
        this.campaniaService = campaniaService;
    }

    @GetMapping
    public ResponseEntity<List<CampaniaDTO>> obtenerTodasLasCampanias() {
        List<CampaniaDTO> campanias = campaniaService.obtenerTodasLasCampanias();
        return ResponseEntity.ok(campanias);
    }

    @PostMapping
    public ResponseEntity<CampaniaDTO> crearCampania(@RequestBody CampaniaDTO campaniaDTO) {
        CampaniaDTO nuevaCampania = campaniaService.crearCampania(campaniaDTO);
        return ResponseEntity.ok(nuevaCampania);
    }
}
