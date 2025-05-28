package com.web.project.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
import org.springframework.security.access.prepost.PreAuthorize;
=======
>>>>>>> 661b8442d2d6ea78fb5c0006aa4ecc5e30213384
import org.springframework.web.bind.annotation.*;

import com.web.project.entity.Usuario;
import com.web.project.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

<<<<<<< HEAD
    @PreAuthorize("hasRole('ADMIN')")
=======
>>>>>>> 661b8442d2d6ea78fb5c0006aa4ecc5e30213384
    @PostMapping
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody Usuario usuario) {
    	
    	try {
<<<<<<< HEAD
            usuarioService.crearUsuario(usuario);
=======
            Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
>>>>>>> 661b8442d2d6ea78fb5c0006aa4ecc5e30213384
            return new ResponseEntity<>("Usuario creado exitosamente", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.CONFLICT);
        }
    }

<<<<<<< HEAD
    @PreAuthorize("hasRole('ADMIN')")
=======
>>>>>>> 661b8442d2d6ea78fb5c0006aa4ecc5e30213384
    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodosLosUsuarios() {
        return new ResponseEntity<>(usuarioService.obtenerTodosLosUsuarios(), HttpStatus.OK);
    }

<<<<<<< HEAD

    @PreAuthorize("hasRole('ADMIN')")
=======
>>>>>>> 661b8442d2d6ea78fb5c0006aa4ecc5e30213384
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Integer id) {
        return usuarioService.obtenerUsuarioPorId(id)
                .map(usuario -> new ResponseEntity<>(usuario, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        Usuario actualizado = usuarioService.actualizarUsuario(id, usuario);
        if (actualizado != null) {
            return new ResponseEntity<>("Usuario actualizado exitosamente", HttpStatus.OK);
        }
        return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
    }

<<<<<<< HEAD
    @PreAuthorize("hasRole('ADMIN')")
=======
>>>>>>> 661b8442d2d6ea78fb5c0006aa4ecc5e30213384
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Integer id) {
        if (usuarioService.eliminarUsuario(id)) {
            return new ResponseEntity<>("Usuario eliminado exitosamente", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
    }
}
