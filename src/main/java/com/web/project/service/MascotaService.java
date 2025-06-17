package com.web.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.web.project.entity.FotosMascota;
import com.web.project.entity.Mascota;
import com.web.project.entity.TipoMascota;
import com.web.project.entity.Usuario;
import com.web.project.dto.FotosDtoRes;
import com.web.project.dto.MascotaDTO;
import com.web.project.dto.MascotaResDTO;
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

    public List<MascotaResDTO> listarPorCliente() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!usuarioRepository.findByEmail(authentication.getName()).isPresent()) throw new RuntimeException("El usuario no existe");    
        Usuario u = usuarioRepository.findByEmail(authentication.getName()).get();
        List<MascotaResDTO> h = new ArrayList<>();
        for(Mascota m : u.getMascotas()) {
            MascotaResDTO nM = new MascotaResDTO();
            nM.setId(m.getId());
            nM.setNombre(m.getNombre());
            nM.setDescripcion(m.getDescripcion());
            nM.setEdad(m.getEdad());
            nM.setTipoId(m.getTipoMascota().getId());
            for(FotosMascota fM : m.getFotosMascotas()) {
                FotosDtoRes fdr = new FotosDtoRes();
                fdr.setUrl(fM.getUrl());
                fdr.setDescripcion(fM.getDescripcion());
                nM.getFotos().add(fdr);
            }
            h.add(nM);
        }
        return h;
    }

    public MascotaDTO create(MascotaDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario a = usuarioRepository.findByEmail(authentication.getName()).orElseThrow(()-> new RuntimeException("El usuario con email " + authentication.getName() + " no existe "));
        
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
        Mascota mascota = new Mascota();
        mascota.setNombre(dto.getNombre());
        mascota.setEdad(dto.getEdad());
        mascota.setDescripcion(dto.getDescripcion());
        mascota.setUsuario(a);
        mascota.setTipoMascota(tipo);
        return toDTO(mascotaRepository.save(mascota));
    }

    public MascotaDTO update(MascotaDTO dto, int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario u = usuarioRepository.findByEmail(authentication.getName()).orElseThrow(()-> new RuntimeException("El usuario no existe"));
        Mascota mascota = mascotaRepository.findById(id).orElseThrow(() -> new NoSuchElementException("La mascota con ID " + id + " no existe."));
        if(!u.getMascotas().contains(mascota)) throw new RuntimeException("La mascota no le corresponde al usuario");
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) throw new IllegalArgumentException("El nombre de la mascota es obligatorio.");
        if (dto.getEdad() == null || dto.getEdad() < 0 || dto.getEdad() > 50) throw new IllegalArgumentException("La edad de la mascota es obligatoria y no puede ser negativa.");
        //if (mascotaRepository.existsByNombreAndUsuario_IdAndIdNot(dto.getNombre(), u.getId(), id)) throw new IllegalArgumentException("Ya existe otra mascota con ese nombre para este cliente.");
        if (dto.getTipoId() != null) {
            TipoMascota tipo = tipoMascotaRepository.findById(dto.getTipoId()).orElseThrow(() -> new NoSuchElementException("El tipo de mascota con ID " + dto.getTipoId() + " no existe."));
            mascota.setTipoMascota(tipo);
        }
        mascota.setNombre(dto.getNombre().trim());
        mascota.setEdad(dto.getEdad());
        mascota.setDescripcion(dto.getDescripcion().trim());
        return toDTO(mascotaRepository.save(mascota));
    }

    public ResponseEntity<?> delete(Integer id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario u = usuarioRepository.findByEmail(authentication.getName()).orElseThrow(()-> new RuntimeException("El usuario con email " + authentication.getName() + " no existe "));
        Mascota m = mascotaRepository.findById(id).orElseThrow(()-> new RuntimeException("La mascota no existe"));
        if(!u.getMascotas().contains(m)) throw new RuntimeException("La mascota no existe");
        mascotaRepository.delete(m);
        return ResponseEntity.ok("La mascota se ha eliminado correctamente");
    }
	
	private MascotaDTO toDTO(Mascota mascota) {
        return MascotaDTO.builder()
                .nombre(mascota.getNombre())
                .descripcion(mascota.getDescripcion())
                .edad(mascota.getEdad())
                .tipoId(mascota.getTipoMascota() != null ? mascota.getTipoMascota().getId() : null)
                .build();
    }

    public MascotaResDTO mascotaPorId(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario u = usuarioRepository.findByEmail(authentication.getName()).orElseThrow(()-> new RuntimeException("No existe el usuario."));
        Mascota mascota = mascotaRepository.findById(id).orElseThrow(() ->new NoSuchElementException("La mascota no existe."));
        if(!u.getMascotas().contains(mascota)) throw new RuntimeException("La mascota no existe.");

        MascotaResDTO r = new MascotaResDTO();
        r.setNombre(mascota.getNombre());
        r.setDescripcion(mascota.getDescripcion());
        r.setEdad(mascota.getEdad());
        r.setId(mascota.getId());
        r.setTipoId(mascota.getTipoMascota().getId());
        
        for(FotosMascota f: mascota.getFotosMascotas()) {
            FotosDtoRes fdr = new FotosDtoRes();
            fdr.setDescripcion(f.getDescripcion());
            fdr.setUrl(f.getUrl());
            r.getFotos().add(fdr);
        }
        return r;
    }
}
