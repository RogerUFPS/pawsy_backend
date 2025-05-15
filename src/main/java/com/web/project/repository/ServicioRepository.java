package com.web.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.project.entity.Servicio;

public interface ServicioRepository extends JpaRepository<Servicio, Integer> {
}
