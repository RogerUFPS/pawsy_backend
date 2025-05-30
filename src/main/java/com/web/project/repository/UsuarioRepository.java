package com.web.project.repository;

import com.web.project.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Integer id);
}
