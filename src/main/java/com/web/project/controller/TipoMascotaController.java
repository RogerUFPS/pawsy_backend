package com.web.project.controller;

import com.web.project.dto.TipoMascotaReq;
import com.web.project.entity.TipoMascota;
import com.web.project.service.TipoMascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-mascota")
@CrossOrigin(origins = "*")
@EnableMethodSecurity
public class TipoMascotaController {

    @Autowired
    private TipoMascotaService tipoMascotaService;

    @GetMapping("/listar")
    public ResponseEntity<List<TipoMascota>> listarTodos() {
        return ResponseEntity.ok(tipoMascotaService.listarTodos());
    }
    
    @GetMapping("/listar/{id}")
    public ResponseEntity<TipoMascota> obtenerPorId(@PathVariable Integer id) {
        TipoMascota tipo = tipoMascotaService.obtenerPorId(id);
        return ResponseEntity.ok(tipo);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<TipoMascota> crear(@RequestBody TipoMascotaReq n) {
        TipoMascota creado = tipoMascotaService.guardar(n);
        return ResponseEntity.ok(creado);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TipoMascota> actualizar(@PathVariable Integer id, @RequestBody TipoMascotaReq n) {
        TipoMascota actualizado = tipoMascotaService.actualizar(id, n);
        return ResponseEntity.ok(actualizado);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        tipoMascotaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

