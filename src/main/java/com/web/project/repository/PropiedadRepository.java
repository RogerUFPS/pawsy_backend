package com.web.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.project.entity.Propiedad;

public interface PropiedadRepository extends JpaRepository<Propiedad, Integer> {
}
