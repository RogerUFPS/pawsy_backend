package com.web.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.web.project.dto.CuidadorDTO;
import com.web.project.dto.UsuarioProfile;
import com.web.project.entity.Usuario;
import com.web.project.service.UsuarioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/perfil")
    public ResponseEntity<UsuarioProfile> getPerfil() {
        return usuarioService.getPerfil();
    }
    
    @GetMapping("/cuidadores")
    public ResponseEntity<List<Usuario>> getCuidadores() {
        return usuarioService.getCuidadores();
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodosLosUsuarios() {
        return ResponseEntity.ok(usuarioService.obtenerTodosLosUsuarios());
    }

    //@PreAuthorize("hasRole('CLIENTE')")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@RequestParam Integer id, @RequestBody Usuario usuario) {
        return usuarioService.actualizarUsuario(usuario);
    }

    //@PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/obtener-cuidadores")
    public ResponseEntity<List<CuidadorDTO>> obtenerCuidadores() {
        return usuarioService.obtenerCuidadores();
    }

    //@PreAuthorize("hasRole('CLIENTE')")
    @PutMapping("/to-cuidador")
    public ResponseEntity<?> serCuidador(@RequestParam Integer id, @RequestParam String telefono) {
        return usuarioService.convertirCuidador(telefono);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
        return usuarioService.crearUsuario(usuario);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Integer id) {
        return null;
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@RequestParam Integer id) {
        return usuarioService.eliminarUsuario(id);
    }
}
