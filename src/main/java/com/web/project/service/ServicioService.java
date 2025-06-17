package com.web.project.service;

import com.web.project.dto.ServicioDTO;
import com.web.project.dto.ServicioResponse;
import com.web.project.entity.Servicio;
import com.web.project.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    public List<ServicioResponse> listarTodos() {
        return servicioRepository.findAll().stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public ServicioResponse obtenerPorId(Integer id) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        return convertirAResponse(servicio);
    }

    public ServicioResponse crear(ServicioDTO dto) {
        Servicio servicio = new Servicio();
        servicio.setNombre(dto.getNombre());
        servicio.setDescripcion(dto.getDescripcion());

        return convertirAResponse(servicioRepository.save(servicio));
    }

    public ServicioResponse actualizar(Integer id, ServicioDTO dto) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

        servicio.setNombre(dto.getNombre());
        servicio.setDescripcion(dto.getDescripcion());

        return convertirAResponse(servicioRepository.save(servicio));
    }

    public void eliminar(Integer id) {
        servicioRepository.deleteById(id);
    }

    public ServicioResponse convertirAResponse(Servicio servicio) {
        return new ServicioResponse(servicio.getId(), servicio.getNombre(), servicio.getDescripcion());
    }
}

