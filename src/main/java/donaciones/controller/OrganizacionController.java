package donaciones.controller;

import donaciones.dto.OrganizacionDTO;
import donaciones.service.IOrganizacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/api/organizaciones")
@CrossOrigin(origins = "*") // Permitir CORS desde cualquier origen
public class OrganizacionController {

    @Autowired
    private IOrganizacionService organizacionService;

    // GET: Listar todas las organizaciones
    @GetMapping
    public ResponseEntity<List<OrganizacionDTO>> listarOrganizaciones() {
        return ResponseEntity.ok(organizacionService.listarOrganizaciones());
    }

    // GET: Obtener una organización por ID
    @GetMapping("/{id}")
    public ResponseEntity<OrganizacionDTO> obtenerOrganizacionPorId(@PathVariable("id") Long id) {
        OrganizacionDTO organizacion = organizacionService.obtenerOrganizacionPorId(id);
        return ResponseEntity.ok(organizacion);
    }

    // POST: Crear una nueva organización
    @PostMapping
    public ResponseEntity<OrganizacionDTO> crearOrganizacion(@Valid @RequestBody OrganizacionDTO organizacionDTO) {
        OrganizacionDTO nuevaOrganizacion = organizacionService.crearOrganizacion(organizacionDTO);
        return ResponseEntity.ok(nuevaOrganizacion);
    }

    // PUT: Actualizar una organización existente
    @PutMapping("/{id}")
    public ResponseEntity<OrganizacionDTO> actualizarOrganizacion(
            @PathVariable("id") Long id,
            @Valid @RequestBody OrganizacionDTO organizacionDTO) {
        OrganizacionDTO actualizada = organizacionService.actualizarOrganizacion(id, organizacionDTO);
        return ResponseEntity.ok(actualizada);
    }

    // DELETE: Eliminar una organización por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<OrganizacionDTO> eliminarOrganizacion(@PathVariable("id") Long id) {
        OrganizacionDTO eliminada = organizacionService.eliminarOrganizacion(id);
        return ResponseEntity.ok(eliminada);
    }

    // GET: Buscar organizaciones por nombre
    @GetMapping("/buscar")
    public ResponseEntity<List<OrganizacionDTO>> buscarPorNombre(@RequestParam("nombre") String nombre) {
        return ResponseEntity.ok(organizacionService.buscarOrganizacionesPorNombre(nombre));
    }
}