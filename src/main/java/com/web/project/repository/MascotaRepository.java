package com.web.project.repository;

import com.web.project.entity.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MascotaRepository extends JpaRepository<Mascota, Integer> {
    List<Mascota> findByUsuarioId(Integer clienteId);
    boolean existsByNombreAndUsuario_Id(String nombre, Integer clienteId);
    boolean existsByNombreAndUsuario_IdAndIdNot(String nombre, Integer clienteId, Integer id);

}
