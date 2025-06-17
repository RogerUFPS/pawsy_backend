package com.web.project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.web.project.dto.CuidadorDTO;
import com.web.project.dto.UsuarioProfile;
import com.web.project.entity.Mascota;
import com.web.project.entity.Propiedad;
import com.web.project.entity.Usuario;
import com.web.project.repository.MascotaRepository;
import com.web.project.repository.PropiedadRepository;
import com.web.project.repository.UsuarioRepository;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MascotaRepository mascotaRepository;
    @Autowired
    private PropiedadRepository propiedadRepository;

    public ResponseEntity<UsuarioProfile> getPerfil() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario a = usuarioRepository.findByEmail(authentication.getName()).orElseThrow(()-> new RuntimeException("El usuario con email " + authentication.getName() + " no existe "));
        UsuarioProfile n = new UsuarioProfile();
        n.setEmail(a.getEmail());
        n.setNombre(a.getNombre());
        n.setTelefono(a.getTelefono()); 
        n.setTipoUsuario(a.getTipoUsuario());
        return ResponseEntity.ok(n);
    }

    public ResponseEntity<List<Usuario>> getCuidadores() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Usuario> ne = usuarioRepository.findByTipoUsuario("CUIDADOR");
        return ResponseEntity.ok(ne);
    }

    public void updateNombre(String nombre) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario a = usuarioRepository.findByEmail(authentication.getName()).orElseThrow(()-> new RuntimeException("El usuario con email " + authentication.getName() + " no existe "));
        a.setNombre(nombre);
        usuarioRepository.save(a);
    }

    private CuidadorDTO toDTO(Usuario u) {
        return CuidadorDTO.builder()
                .id(u.getId())
                .nombre(u.getNombre())
                .email(u.getEmail())
                .telefono(u.getTelefono())
                .propiedades(u.getPropiedades())
                .build();
    }

    public ResponseEntity<?> eliminarUsuario(Integer id) {
        Usuario a = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("El usuario no existe"));
        usuarioRepository.delete(a);
        return ResponseEntity.ok("Se ha eliminado el usuario correctamente");
    }

    public void convertirCuidador(String telefono) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario a = usuarioRepository.findByEmail(authentication.getName()).orElseThrow(()->new RuntimeException("El usuario no existe"));
        if(!a.isVerificado()) throw new RuntimeException("El usuario no esta verificado");
        a.setTelefono(telefono);
        a.setTipoUsuario("CUIDADOR");
        usuarioRepository.save(a);
    }
}
