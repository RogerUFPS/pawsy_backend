package com.web.project.controller;

import com.web.project.entity.TipoMascota;
import com.web.project.service.TipoMascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-mascota")
@CrossOrigin(origins = "*") // Opcional si consumes desde el frontend
public class TipoMascotaController {

    @Autowired
    private TipoMascotaService tipoMascotaService;

    // Listar todos
    @GetMapping
    public ResponseEntity<List<TipoMascota>> listarTodos() {
        return ResponseEntity.ok(tipoMascotaService.listarTodos());
    }

    // Obtener uno por ID
    @GetMapping("/{id}")
    public ResponseEntity<TipoMascota> obtenerPorId(@PathVariable Integer id) {
        TipoMascota tipo = tipoMascotaService.obtenerPorId(id);
        return ResponseEntity.ok(tipo);
    }

    // Crear nuevo tipo
    @PostMapping
    public ResponseEntity<TipoMascota> crear(@RequestBody TipoMascota tipoMascota) {
        TipoMascota creado = tipoMascotaService.guardar(tipoMascota);
        return ResponseEntity.ok(creado);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<TipoMascota> actualizar(@PathVariable Integer id, @RequestBody TipoMascota tipoMascota) {
        TipoMascota actualizado = tipoMascotaService.actualizar(id, tipoMascota);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        tipoMascotaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

