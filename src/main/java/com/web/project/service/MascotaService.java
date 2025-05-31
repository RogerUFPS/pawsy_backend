package com.web.project.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.web.project.entity.Mascota;
import com.web.project.entity.TipoMascota;
import com.web.project.entity.Usuario;
import com.web.project.dto.MascotaDTO;
import com.web.project.repository.MascotaRepository;
import com.web.project.repository.TipoMascotaRepository;
import com.web.project.repository.UsuarioRepository;

@Service
public class MascotaService {
	
    @Autowired
    private MascotaRepository mascotaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TipoMascotaRepository tipoMascotaRepository;
	
	public List<MascotaDTO> listAll(){
		return mascotaRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

    public List<MascotaDTO> listarPorUsuario_Admin(Integer clienteId) {
        // Verifica si el usuario existe
        if (!usuarioRepository.existsById(clienteId)) {
            throw new NoSuchElementException("El cliente con ID " + clienteId + " no existe.");
        }

        return mascotaRepository.findByUsuarioId(clienteId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    //Peticion realizada una vez logeado
    public List<MascotaDTO> listarPorCliente() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!usuarioRepository.findByEmail(authentication.getName()).isPresent()){
            throw new RuntimeException("El usuario no existe");    
        }
        Usuario u = usuarioRepository.findByEmail(authentication.getName()).get();
        return mascotaRepository.findByUsuarioId(u.getId()).stream().map(this::toDTO).collect(Collectors.toList());
    }

    //Peticion realizada una vez logeado
    public MascotaDTO create(MascotaDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!usuarioRepository.findByEmail(authentication.getName()).isPresent()){
            throw new RuntimeException("El usuario no existe");    
        }
        Usuario u = usuarioRepository.findByEmail(authentication.getName()).get();

        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la mascota es obligatorio.");
        }

        TipoMascota tipo;
        if (dto.getTipoId() != null) {
            tipo = tipoMascotaRepository.findById(dto.getTipoId())
                    .orElseThrow(() -> new NoSuchElementException("El tipo de mascota con ID " + dto.getTipoId() + " no existe."));
        }else {
            throw new IllegalArgumentException("El campo tipo de mascota es obligatorio.");
        }

        if (dto.getEdad() == null || dto.getEdad() < 0 || dto.getEdad() > 50){
            throw new IllegalArgumentException("La edad de la mascota es obligatoria y no puede ser negativa.");
        }

        // if (dto.getDescripcion() == null || dto.getDescripcion().trim().isEmpty()) {
        //     throw new IllegalArgumentException("La descripción de la mascota es obligatoria.");
        // }

        Mascota mascota = new Mascota();
        mascota.setNombre(dto.getNombre());
        mascota.setEdad(dto.getEdad());
        mascota.setDescripcion(dto.getDescripcion());
        mascota.setUsuario(u);
        mascota.setTipoMascota(tipo);

        return toDTO(mascotaRepository.save(mascota));
    }

    //Peticion realizada una vez logeado
    public MascotaDTO update(MascotaDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!usuarioRepository.findByEmail(authentication.getName()).isPresent()){
            throw new RuntimeException("El usuario no existe");    
        }
        Usuario u = usuarioRepository.findByEmail(authentication.getName()).get();

        Mascota mascota = mascotaRepository.findById(dto.getId())
                .orElseThrow(() -> new NoSuchElementException("La mascota con ID " + dto.getId() + " no existe."));

        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la mascota es obligatorio.");
        }

        if (dto.getEdad() == null || dto.getEdad() < 0 || dto.getEdad() > 50){
            throw new IllegalArgumentException("La edad de la mascota es obligatoria y no puede ser negativa.");
        }

        // if (dto.getDescripcion() == null || dto.getDescripcion().trim().isEmpty()) {
        //     throw new IllegalArgumentException("La descripción de la mascota es obligatoria.");
        // }

        // Validar unicidad del nombre por cliente, ignorando la mascota actual
        if (mascotaRepository.existsByNombreAndUsuario_IdAndIdNot(dto.getNombre(), u.getId(), dto.getId())) {
            throw new IllegalArgumentException("Ya existe otra mascota con ese nombre para este cliente.");
        }

        if (dto.getTipoId() != null) {
            TipoMascota tipo = tipoMascotaRepository.findById(dto.getTipoId())
                    .orElseThrow(() -> new NoSuchElementException("El tipo de mascota con ID " + dto.getTipoId() + " no existe."));
            mascota.setTipoMascota(tipo);
        }

        mascota.setNombre(dto.getNombre().trim());
        mascota.setEdad(dto.getEdad());
        mascota.setDescripcion(dto.getDescripcion().trim());

        return toDTO(mascotaRepository.save(mascota));
    }

    //Peticion realizada una vez logeado
    public ResponseEntity<?> delete(Integer id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!usuarioRepository.findByEmail(authentication.getName()).isPresent()){
            throw new RuntimeException("El usuario no existe");    
        }
        Usuario u = usuarioRepository.findByEmail(authentication.getName()).get();
        
        if(!mascotaRepository.findById(id).isPresent()) throw new RuntimeException("Mascota no existe");

        if(!u.getMascotas().contains(mascotaRepository.findById(id).get())) throw new RuntimeException("Esta mascota no existe para este usuario");
        
        Mascota m = mascotaRepository.findById(id).get();
        mascotaRepository.delete(m);

        return ResponseEntity.ok("La mascota se ha eliminado correctamente");
    }
	
	
	//Convertir la entidad Mascota en un objeto MascotaDTO
	private MascotaDTO toDTO(Mascota mascota) {
        return MascotaDTO.builder()
                .id(mascota.getId())
                .nombre(mascota.getNombre())
                .descripcion(mascota.getDescripcion())
                .edad(mascota.getEdad())
                .clienteId(mascota.getUsuario().getId())
                .tipoId(mascota.getTipoMascota() != null ? mascota.getTipoMascota().getId() : null)
                .build();
    }


    public MascotaDTO findById(Integer id) {
        Mascota mascota = mascotaRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("La mascota con ID " + id + " no existe."));
        return toDTO(mascota);
    }
}
