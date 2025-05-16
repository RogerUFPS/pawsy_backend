package com.web.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.project.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	boolean existsByEmail(String email);
    boolean existsByTelefono(String telefono);
    
}
