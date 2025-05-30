package com.web.project.controller;

import com.web.project.entity.Usuario;
import com.web.project.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.getAllUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.getUsuarioById(id));
    }

    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@Valid @RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.saveUsuario(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(
            @PathVariable Integer id,
            @Valid @RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.updateUsuario(id, usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
