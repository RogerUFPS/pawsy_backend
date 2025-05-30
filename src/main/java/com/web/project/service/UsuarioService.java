package com.web.project.service;

import com.web.project.entity.Usuario;
import com.web.project.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario getUsuarioById(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));
    }

    public Usuario saveUsuario(Usuario usuario) {
        validateUsuario(usuario, true);
        return usuarioRepository.save(usuario);
    }

    public Usuario updateUsuario(Integer id, Usuario updatedUsuario) {
        Usuario existing = getUsuarioById(id);
        validateUsuario(updatedUsuario, false);

        existing.setNombre(updatedUsuario.getNombre());
        existing.setClave(updatedUsuario.getClave());
        existing.setEmail(updatedUsuario.getEmail());
        existing.setTelefono(updatedUsuario.getTelefono());
        existing.setDireccion(updatedUsuario.getDireccion());
        existing.setTipoUsuario(updatedUsuario.getTipoUsuario());

        return usuarioRepository.save(existing);
    }

    public void deleteUsuario(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar. Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    private void validateUsuario(Usuario usuario, boolean isNew) {
        if (!StringUtils.hasText(usuario.getNombre())) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }

        if (!StringUtils.hasText(usuario.getClave())) {
            throw new IllegalArgumentException("La clave es obligatoria");
        }

        if (!StringUtils.hasText(usuario.getEmail())) {
            throw new IllegalArgumentException("El email es obligatorio");
        }

        if (!StringUtils.hasText(usuario.getTelefono())) {
            throw new IllegalArgumentException("El teléfono es obligatorio");
        }

        if (!StringUtils.hasText(usuario.getTipoUsuario())) {
            throw new IllegalArgumentException("El tipo de usuario es obligatorio");
        }

        if (!usuario.getTipoUsuario().equalsIgnoreCase("cuidador") &&
            !usuario.getTipoUsuario().equalsIgnoreCase("cliente")) {
            throw new IllegalArgumentException("El tipo de usuario debe ser 'cuidador' o 'cliente'");
        }

        if (isNew && usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese email");
        }
    }
}
