package com.web.project.repository;

<<<<<<< HEAD
import com.web.project.entity.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MascotaRepository extends JpaRepository<Mascota, Integer> {
    List<Mascota> findByUsuarioId(Integer clienteId);
}

=======
import org.springframework.data.jpa.repository.JpaRepository;

import com.web.project.entity.Mascota;

public interface MascotaRepository extends JpaRepository<Mascota, Integer> {

}
>>>>>>> Gabriel
