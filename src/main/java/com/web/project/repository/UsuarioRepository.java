package com.web.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.project.entity.Usuario;
import java.util.List;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	boolean existsByEmail(String email);
    boolean existsByTelefono(String telefono);
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByTipoUsuario(String tipoUsuario);
}
