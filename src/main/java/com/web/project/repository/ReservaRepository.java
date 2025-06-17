package com.web.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.project.entity.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
}
