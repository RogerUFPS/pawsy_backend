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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

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

    @PutMapping("/to-cuidador/{telefono}")
    public ResponseEntity<?> serCuidador(@PathVariable String telefono) {
        usuarioService.convertirCuidador(telefono);
        return ResponseEntity.ok("Se ha convertido a cuidador");
    }

    @PutMapping("/{nombre}")
    public ResponseEntity<?> actualizarNombre(@PathVariable String nombre) {
        usuarioService.updateNombre(nombre);
        return ResponseEntity.ok("Se ha actualizado el nombre");
    }

}
