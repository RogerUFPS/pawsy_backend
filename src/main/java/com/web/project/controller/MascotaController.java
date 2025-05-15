package com.web.project.controller;

import com.web.project.entity.dto.MascotaDTO;
import com.web.project.service.MascotaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController	
@RequestMapping("/mascota")	
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

    // Obtener una mascota por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerMascotaPorId(@PathVariable Integer id) {
        MascotaDTO mascota = mascotaService.findById(id);
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Mascota encontrada correctamente.");
        response.put("datos", mascota);
        return ResponseEntity.ok(response);
    }


    // Obtener mascotas por usuario
        @GetMapping("/usuario/{clienteId}")
        public ResponseEntity<List<MascotaDTO>> obtenerPorUsuario(@PathVariable Integer clienteId) {
            List<MascotaDTO> mascotas = mascotaService.listarPorUsuario(clienteId);
            return ResponseEntity.ok(mascotas);
        }

        // Crear una nueva mascota
        @PostMapping
        public ResponseEntity<Map<String, Object>> crearMascota(@Valid @RequestBody MascotaDTO dto) {
            MascotaDTO nuevaMascota = mascotaService.create(dto);
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Mascota creada exitosamente.");
            response.put("datos", nuevaMascota);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        // Actualizar una mascota existente
        @PutMapping("/{id}")
        public ResponseEntity<Map<String, Object>> actualizarMascota(@PathVariable Integer id, @Valid @RequestBody MascotaDTO dto) {
            MascotaDTO mascotaActualizada = mascotaService.update(id, dto);
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Mascota actualizada correctamente.");
            response.put("datos", mascotaActualizada);
            return ResponseEntity.ok(response);
        }

        // Eliminar una mascota
        @DeleteMapping("/{id}")
        public ResponseEntity<Map<String, String>> eliminarMascota(@PathVariable Integer id) {
            mascotaService.delete(id);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Mascota eliminada exitosamente.");
            return ResponseEntity.ok(response);
        }
}
