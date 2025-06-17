package com.web.project.controller;

import com.web.project.dto.ServicioDTO;
import com.web.project.dto.ServicioResponse;
import com.web.project.service.ServicesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = "*")
@EnableMethodSecurity
public class ServicioController {

    @Autowired
    private ServicesService servicioService;

    @GetMapping("/listar")
    public List<ServicioResponse> listar() {
        return servicioService.listarTodos();
    }
    @PreAuthorize("hasAnyRole('CLIENTE', 'CUIDADOR')")
    @GetMapping("/listar/{id}")
    public ResponseEntity<ServicioResponse> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioService.obtenerPorId(id));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ServicioResponse> crear(@Valid @RequestBody ServicioDTO dto) {
        return ResponseEntity.ok(servicioService.crear(dto));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @Valid @RequestBody ServicioDTO dto) {
        ResponseEntity.ok(servicioService.actualizar(id, dto));
        return ResponseEntity.ok("Se ha actualizado correctamente");
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        servicioService.eliminar(id);
        return ResponseEntity.ok("Se ha eliminado correctamente");
    }
}
