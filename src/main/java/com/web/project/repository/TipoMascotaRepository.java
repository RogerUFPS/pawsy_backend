package com.web.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.project.entity.TipoMascota;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoMascotaRepository extends JpaRepository<TipoMascota, Integer>{
}
