package com.web.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.project.entity.Mascota;

public interface MascotaRepository extends JpaRepository<Mascota, Integer> {

}
