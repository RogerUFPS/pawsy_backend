package com.web.project.controller;

import com.web.project.dto.MascotaDTO;
import com.web.project.dto.MascotaResDTO;
import com.web.project.service.MascotaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/mascota")
public class MascotaController {

	@Autowired
	private MascotaService mascotaService;


	@GetMapping("/lista-mascotas")
	public ResponseEntity<List<MascotaResDTO>> listaMascotasCliente() {
		return ResponseEntity.ok(mascotaService.listarPorCliente());
	}
	
	@PostMapping
	public ResponseEntity<MascotaDTO> crearMascota(@RequestBody MascotaDTO dto) {
		return ResponseEntity.ok(mascotaService.create(dto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<MascotaDTO> actualizarMascota(@RequestBody MascotaDTO dto, @PathVariable int id) {
		MascotaDTO mascotaActualizada = mascotaService.update(dto, id);
		return ResponseEntity.ok(mascotaActualizada);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarMascota(@PathVariable Integer id) {
        return mascotaService.delete(id);
	}

	@GetMapping("/{id}")
	public ResponseEntity<MascotaResDTO> obtenerMascotaPorId(@PathVariable Integer id) {
		return ResponseEntity.ok(mascotaService.mascotaPorId(id));
	}
}
