package com.web.project.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.web.project.entity.Usuario;
import com.web.project.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody Usuario usuario) {
    	
    	try {
            Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
            return new ResponseEntity<>("Usuario creado exitosamente", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodosLosUsuarios() {
        return new ResponseEntity<>(usuarioService.obtenerTodosLosUsuarios(), HttpStatus.OK);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Integer id) {
        if (usuarioService.eliminarUsuario(id)) {
            return new ResponseEntity<>("Usuario eliminado exitosamente", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
    }
}
