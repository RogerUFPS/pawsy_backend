package com.web.project.controllers;

import com.web.project.entity.dto.MascotaDTO;
import com.web.project.services.MascotaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    // Obtener todas las mascotas
    @GetMapping
    public ResponseEntity<List<MascotaDTO>> obtenerTodas() {
        List<MascotaDTO> mascotas = mascotaService.listAll();
        return ResponseEntity.ok(mascotas);
    }

    // Obtener mascotas por usuario
    @GetMapping("/usuario/{clienteId}")
    public ResponseEntity<List<MascotaDTO>> obtenerPorUsuario(@PathVariable Integer clienteId) {
        List<MascotaDTO> mascotas = mascotaService.listarPorUsuario(clienteId);
        return ResponseEntity.ok(mascotas);
    }

    // Crear una nueva mascota
    @PostMapping
    public ResponseEntity<MascotaDTO> crearMascota(@Valid @RequestBody MascotaDTO dto) {
        MascotaDTO nuevaMascota = mascotaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMascota);
    }

    // Actualizar una mascota existente
    @PutMapping("/{id}")
    public ResponseEntity<MascotaDTO> actualizarMascota(@PathVariable Integer id, @Valid @RequestBody MascotaDTO dto) {
        MascotaDTO mascotaActualizada = mascotaService.update(id, dto);
        return ResponseEntity.ok(mascotaActualizada);
    }

    // Eliminar una mascota
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMascota(@PathVariable Integer id) {
        mascotaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
