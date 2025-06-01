package com.web.project.controller;

import com.web.project.dto.PropiedadDTO;
import com.web.project.dto.PropiedadResponse;
import com.web.project.dto.ServicioResponse;
import com.web.project.dto.UsuarioResumen;
import com.web.project.entity.Propiedad;
import com.web.project.entity.Usuario;
import com.web.project.repository.PropiedadRepository;
import com.web.project.service.PropiedadService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/propiedades")
@CrossOrigin(origins = "*")
public class PropiedadController {

    @Autowired
    private PropiedadService propiedadService;

    @GetMapping
    public List<PropiedadResponse> listarPropiedades() {
        return propiedadService.listarPropiedades();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropiedadResponse> obtenerPropiedadPorId(@PathVariable Integer id) {
        Propiedad propiedad = propiedadService.obtenerPorId(id);
        PropiedadResponse response = propiedadService.convertirAResponse(propiedad);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> crearPropiedad(@Valid @RequestBody PropiedadDTO dto) {
        propiedadService.crearPropiedad(dto);
        return ResponseEntity.ok("Propiedad registrada correctamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<PropiedadResponse> actualizar(@PathVariable Integer id,
            @Valid @RequestBody PropiedadDTO dto) {
        Propiedad actualizada = propiedadService.actualizar(id, dto);
        PropiedadResponse response = propiedadService.convertirAResponse(actualizada);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        propiedadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
