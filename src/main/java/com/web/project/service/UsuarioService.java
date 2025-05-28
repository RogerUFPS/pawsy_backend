package com.web.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.project.entity.Usuario;
import com.web.project.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario crearUsuario(Usuario usuario) {
    	
    	if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        if (usuarioRepository.existsByTelefono(usuario.getTelefono())) {
            throw new IllegalArgumentException("El teléfono ya está registrado");
        }
    	
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Usuario actualizarUsuario(Integer id, Usuario usuarioActualizado) {
    	
    	
    	if(usuarioActualizado.getNombre() == null) {
    		
    	}
    	
    	try {
	        return usuarioRepository.findById(id).map(usuario -> {
	            usuario.setNombre(usuarioActualizado.getNombre());
	            usuario.setEmail(usuarioActualizado.getEmail());
	            usuario.setDireccion(usuarioActualizado.getDireccion());
	            usuario.setTelefono(usuarioActualizado.getTelefono());
	            usuario.setTipoUsuario(usuarioActualizado.getTipoUsuario());
	            return usuarioRepository.save(usuario);
	        }).orElse(null);
    	}catch(Exception e) {
    		return null;
    	}
    }

    public boolean eliminarUsuario(Integer id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
