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

    @Autowired
    private PropiedadRepository propiedadRepository;

    @GetMapping("/propiedades")
    public List<PropiedadResponse> listarPropiedades() {
        return propiedadRepository.findAll().stream().map(propiedad -> {
            Usuario u = propiedad.getUsuario();
            UsuarioResumen user = new UsuarioResumen(u.getId(), u.getNombre(), u.getEmail());

            List<ServicioResponse> servicios = propiedad.getServicios().stream()
                    .map(s -> new ServicioResponse(s.getId(), s.getNombre()))
                    .toList();

            return new PropiedadResponse(
                    propiedad.getId(),
                    propiedad.getNombre(),
                    propiedad.getDireccion(),
                    propiedad.getDescripcion(),
                    propiedad.getCapacidad(),
                    propiedad.getPrecioPorNoche(),
                    user,
                    servicios);
        }).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Propiedad> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(propiedadService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<?> crearPropiedad(@Valid @RequestBody PropiedadDTO dto) {
        propiedadService.crearPropiedad(dto);
        return ResponseEntity.ok("Propiedad registrada correctamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Propiedad> actualizar(@PathVariable Integer id, @RequestBody PropiedadDTO propiedad) {
        return ResponseEntity.ok(propiedadService.actualizar(id, propiedad));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        propiedadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
