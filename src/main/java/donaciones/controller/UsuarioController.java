package donaciones.controller;

import donaciones.dto.response.UsuarioResponse;
import donaciones.dto.request.RegisterRequest;

import donaciones.service.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final IUsuarioService usuarioService;

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> getCurrentUsuario() {
        return ResponseEntity.ok(usuarioService.getCurrentUsuario());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponse> getUsuarioById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.getUsuarioById(id));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.getAllUsuarios());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponse> createUsuario(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(usuarioService.createUsuario(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> updateUsuario(
            @PathVariable Long id,
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(usuarioService.updateUsuario(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @usuarioService.getUsuarioEntity(#id).email == authentication.principal.username")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/organizacion")
    public ResponseEntity<Long> getOrganizacionIdIfOwner(@PathVariable Long id) {
        return ResponseEntity.of(
            Optional.ofNullable(usuarioService.getOrganizacionIdIfOwner(id))
        );
    }
}