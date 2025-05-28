package com.web.project.security;

import com.web.project.entity.Usuario;
import com.web.project.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));
        List<String> roles = new ArrayList<>();

        //se guardan con el tipo USER, CUIDADOR o ADMIN
        roles.add(usuario.getTipoUsuario());

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getClave())
                .roles(roles.toArray(new String[0]))
                .build();
    }
}