package com.web.project.controller;

import com.web.project.dto.ServicioDTO;
import com.web.project.dto.ServicioResponse;
import com.web.project.service.ServicioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = "*")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @GetMapping
    public List<ServicioResponse> listar() {
        return servicioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioResponse> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ServicioResponse> crear(@Valid @RequestBody ServicioDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicioResponse> actualizar(@PathVariable Integer id, @Valid @RequestBody ServicioDTO dto) {
        return ResponseEntity.ok(servicioService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        servicioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

