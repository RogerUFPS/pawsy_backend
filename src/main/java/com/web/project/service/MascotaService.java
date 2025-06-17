package com.web.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.web.project.entity.FotosMascota;
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
import org.springframework.web.multipart.MultipartFile;

@Service
public class MascotaService {
	
    @Autowired
    private MascotaRepository mascotaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TipoMascotaRepository tipoMascotaRepository;

    @Autowired
    private CloudinaryService cloudinaryService;
	
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
    public MascotaDTO create(MascotaDTO dto, MultipartFile foto) {
        String urlImagen = cloudinaryService.subirImagen(foto).toString();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario u = usuarioRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("El usuario no existe"));

        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la mascota es obligatorio.");
        }

        if (dto.getEdad() == null || dto.getEdad() < 0 || dto.getEdad() > 50){
            throw new IllegalArgumentException("La edad de la mascota es obligatoria y válida.");
        }

        TipoMascota tipo = tipoMascotaRepository.findById(dto.getTipoId())
                .orElseThrow(() -> new NoSuchElementException("Tipo de mascota no existe"));

        Mascota mascota = new Mascota();
        mascota.setNombre(dto.getNombre());
        mascota.setEdad(dto.getEdad());
        mascota.setDescripcion(dto.getDescripcion());
        mascota.setUsuario(u);
        mascota.setTipoMascota(tipo);

        Map<String, String> uploadResult = cloudinaryService.subirImagen(foto);

        FotosMascota fotoMascota = new FotosMascota();
        fotoMascota.setUrl(uploadResult.get("url"));
        fotoMascota.setPublicId(uploadResult.get("public_id"));
        fotoMascota.setMascota(mascota);


        return toDTO(mascotaRepository.save(mascota));
    }


    //Peticion realizada una vez logeado
    public MascotaDTO update(MascotaDTO dto, int id, MultipartFile nuevaFoto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario u = usuarioRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("El usuario no existe"));

        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("La mascota con ID " + id + " no existe."));

        // Validación de propiedad del recurso
        if (!mascota.getUsuario().getId().equals(u.getId())) {
            throw new RuntimeException("Esta mascota no pertenece al usuario autenticado.");
        }

        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la mascota es obligatorio.");
        }

        if (dto.getEdad() == null || dto.getEdad() < 0 || dto.getEdad() > 50) {
            throw new IllegalArgumentException("La edad de la mascota es obligatoria y debe estar entre 0 y 50.");
        }

        if (mascotaRepository.existsByNombreAndUsuario_IdAndIdNot(dto.getNombre(), u.getId(), id)) {
            throw new IllegalArgumentException("Ya existe otra mascota con ese nombre para este cliente.");
        }

        if (dto.getTipoId() != null) {
            TipoMascota tipo = tipoMascotaRepository.findById(dto.getTipoId())
                    .orElseThrow(() -> new NoSuchElementException("El tipo de mascota con ID " + dto.getTipoId() + " no existe."));
            mascota.setTipoMascota(tipo);
        }

        mascota.setNombre(dto.getNombre().trim());
        mascota.setEdad(dto.getEdad());
        mascota.setDescripcion(dto.getDescripcion() != null ? dto.getDescripcion().trim() : null);

        // 🔁 Actualizar la imagen si se envía una nueva
        if (nuevaFoto != null && !nuevaFoto.isEmpty()) {
            // Eliminar la anterior de Cloudinary (si existe)
            if (mascota.getFotosMascotas() != null && !mascota.getFotosMascotas().isEmpty()) {
                for (FotosMascota foto : mascota.getFotosMascotas()) {
                    cloudinaryService.eliminarImagen(foto.getPublicId());
                }
                mascota.getFotosMascotas().clear(); // Limpiar lista
            }

            // Subir nueva foto
            String nuevaUrl = cloudinaryService.subirImagen(nuevaFoto).toString();
            FotosMascota fotoMascota = new FotosMascota();
            fotoMascota.setUrl(nuevaUrl);
            fotoMascota.setPublicId(cloudinaryService.extraerPublicId(nuevaUrl));
            fotoMascota.setMascota(mascota);

            mascota.getFotosMascotas().add(fotoMascota);
        }

        return toDTO(mascotaRepository.save(mascota));
    }


    //Peticion realizada una vez logeado
    public ResponseEntity<?> delete(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario u = usuarioRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("El usuario con email " + authentication.getName() + " no existe"));

        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no existe"));

        if (!u.getMascotas().contains(mascota)) {
            throw new RuntimeException("Esta mascota no existe para este usuario");
        }

        // Borrar imágenes de Cloudinary
        if (mascota.getFotosMascotas() != null) {
            for (FotosMascota foto : mascota.getFotosMascotas()) {
                cloudinaryService.eliminarImagen(foto.getPublicId());
            }
        }

        mascotaRepository.delete(mascota);

        return ResponseEntity.ok("La mascota se ha eliminado correctamente");
    }



    //Convertir la entidad Mascota en un objeto MascotaDTO
    public MascotaDTO toDTO(Mascota mascota) {
        String urlFoto = null;
        if (mascota.getFotosMascotas() != null && !mascota.getFotosMascotas().isEmpty()) {
            urlFoto = mascota.getFotosMascotas().get(0).getUrl();
        }

        return MascotaDTO.builder()
                .nombre(mascota.getNombre())
                .descripcion(mascota.getDescripcion())
                .edad(mascota.getEdad())
                .tipoId(mascota.getTipoMascota().getId())
                .urlFoto(urlFoto)
                .build();
    }




    public MascotaDTO findById(Integer id) {
        Mascota mascota = mascotaRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("La mascota con ID " + id + " no existe."));
        return toDTO(mascota);
    }
}
