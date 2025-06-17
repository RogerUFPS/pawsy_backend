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

import io.micrometer.core.ipc.http.HttpSender.Response;

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
        return ResponseEntity.ok(n);
    }

    public ResponseEntity<List<Usuario>> getCuidadores() {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Usuario> ne = usuarioRepository.findByTipoUsuario("CUIDADOR");
        return ResponseEntity.ok(ne);
    }   

    public ResponseEntity<?> crearUsuario(Usuario usuario) {
    	if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Se ha creado el usuario correctamente");
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    public ResponseEntity<List<CuidadorDTO>> obtenerCuidadores() {
        List<Usuario> s = usuarioRepository.findByTipoUsuario("CUIDADOR");
        return ResponseEntity.ok(s.stream().map(this::toDTO).collect(Collectors.toList()));
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

    //Peticion realizada cuando el usuario este logeado
    public ResponseEntity<?> actualizarUsuario(Usuario ua) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!usuarioRepository.findByEmail(authentication.getName()).isPresent()){
            throw new RuntimeException("El usuario no existe");    
        }
        Usuario a = usuarioRepository.findByEmail(authentication.getName()).get();
        if(!a.isVerificado()) {
            throw new RuntimeException("El usuario no esta verificado");
        }
        a.setNombre(ua.getNombre());
        a.setEmail(ua.getEmail());
        //a.setDireccion(ua.getDireccion());
        a.setTelefono(ua.getTelefono());
        //a.setDireccion(ua.getDireccion());
        a.setTipoUsuario(ua.getTipoUsuario());
        usuarioRepository.save(a);

        return ResponseEntity.ok("Se ha actualizado el usuario");

    }

    //Peticion por admin
    public ResponseEntity<?> eliminarUsuario(Integer id) {

        Usuario a = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("El usuario no existe"));

        usuarioRepository.delete(a);

        return ResponseEntity.ok("Se ha eliminado el usuario correctamente");
    }

    //Peticion realizada cuando el usuario esta logeado
    public ResponseEntity<?> convertirCuidador(String telefono) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!usuarioRepository.findByEmail(authentication.getName()).isPresent()){
            throw new RuntimeException("El usuario no existe");    
        }
        Usuario a = usuarioRepository.findByEmail(authentication.getName()).get();
        
        if(!a.isVerificado()) {
            throw new RuntimeException("El usuario no esta verificado, no puede convertirse en cuidador");
        }
        //a.setDireccion(direccion);
        a.setTelefono(telefono);
        a.setTipoUsuario("CUIDADOR");
        a.getRoles().add("CUIDADOR");
        usuarioRepository.save(a);
    
        return ResponseEntity.ok("Se ha convertido en un cuidador");
    }
}
