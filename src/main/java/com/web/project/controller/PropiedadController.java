package com.web.project.controller;

import com.web.project.dto.PropiedadDTO;
import com.web.project.dto.PropiedadRes;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/propiedades")
@CrossOrigin(origins = "*")
public class PropiedadController {

    @Autowired
    private PropiedadService propiedadService;

    @GetMapping()
    public ResponseEntity<List<PropiedadRes>> listarPropiedades() {
        return ResponseEntity.ok(propiedadService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropiedadRes> propiedadPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(propiedadService.obtenerPropiedadPorId(id));
    }

    @PreAuthorize("hasRole('CUIDADOR')")
    @GetMapping("/lista-propiedades")
    public ResponseEntity<List<PropiedadRes>> listaPropiedadesPropias() {
        return ResponseEntity.ok(propiedadService.obtenerPropiedadesCuidador());
    }

    @PreAuthorize("hasRole('CUIDADOR')")
    @PostMapping()
    public ResponseEntity<?> crearPropiedad(@RequestBody PropiedadDTO dto) {
        propiedadService.crearPropiedad(dto);
        return ResponseEntity.ok("Propiedad registrada correctamente");
    }

    @PreAuthorize("hasRole('CUIDADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody PropiedadDTO propiedad) {
        propiedadService.actualizar(propiedad, id);
        return ResponseEntity.ok("Se ha actualizado la propiedad");
    }

    @PreAuthorize("hasRole('CUIDADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        propiedadService.eliminar(id);
        return ResponseEntity.ok("Se ha eliminado correctamente la propiedad");
    }
}
