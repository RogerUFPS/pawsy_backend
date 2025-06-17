package com.web.project.controller;

import com.web.project.dto.MascotaDTO;
import com.web.project.service.MascotaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

	// Obtener mascotas por usuario
	//@PreAuthorize("hasRole('CLIENTE')")
	@GetMapping("/usuario/{clienteId}")
	public ResponseEntity<?> obtenerPorUsuario(@PathVariable Integer id) {
        List<MascotaDTO> mascotas = mascotaService.listarPorCliente();
        return ResponseEntity.ok(mascotas);
	}

	@GetMapping("/lista-mascotas")
	public ResponseEntity<List<MascotaDTO>> listaMascotasCliente() {
		return ResponseEntity.ok(mascotaService.listarPorCliente());
	}
	

	// Crear una nueva mascota
	//@PreAuthorize("hasRole('CLIENTE')")
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<MascotaDTO> crearMascota(@RequestParam String nombre,
												   @RequestParam(required = false) String descripcion,
												   @RequestParam Integer edad,
												   @RequestParam Integer clienteId,
												   @RequestParam Integer tipoId,
												   @RequestParam MultipartFile foto) {
		MascotaDTO dto = MascotaDTO.builder()
				.nombre(nombre)
				.descripcion(descripcion)
				.edad(edad)
				.clienteId(clienteId)
				.tipoId(tipoId)
				.build();

		MascotaDTO mascota = mascotaService.create(dto, foto);
		return ResponseEntity.ok(mascota);
		// Map<String, Object> response = new HashMap<>();
		// response.put("mensaje", "Mascota creada exitosamente.");
		// response.put("datos", nuevaMascota);
		//return ResponseEntity.status(HttpStatus.CREATED).body(response);

	}

	// Actualizar una mascota existente
	//@PreAuthorize("hasRole('CLIENTE')")
	@PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<MascotaDTO> actualizarMascota(
			@PathVariable int id,
			@RequestPart("mascota") MascotaDTO dto,
			@RequestPart(value = "foto", required = false) MultipartFile nuevaFoto
	) {
		MascotaDTO actualizada = mascotaService.update(dto, id, nuevaFoto);
		return ResponseEntity.ok(actualizada);
	}


	// Eliminar una mascota
	//@PreAuthorize("hasRole('CLIENTE')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarMascota(@RequestParam Integer id) {
        return mascotaService.delete(id);
	}

	// Obtener todas las mascotas
	//@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<MascotaDTO>> obtenerTodas() {
		List<MascotaDTO> mascotas = mascotaService.listAll();
		return ResponseEntity.ok(mascotas);
	}

	// Obtener una mascota por ID
	//@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> obtenerMascotaPorId(@PathVariable Integer id) {
		MascotaDTO mascota = mascotaService.findById(id);
		Map<String, Object> response = new HashMap<>();
		response.put("mensaje", "Mascota encontrada correctamente.");
		response.put("datos", mascota);
		return ResponseEntity.ok(response);
	}
}
