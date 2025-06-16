package com.web.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.project.dto.PropiedadDTO;
import com.web.project.dto.PropiedadResponse;
import com.web.project.dto.ServicioResponse;
import com.web.project.dto.UsuarioResumen;
import com.web.project.entity.Propiedad;
import com.web.project.entity.Servicio;
import com.web.project.entity.Usuario;
import com.web.project.repository.PropiedadRepository;
import com.web.project.repository.ServicioRepository;
import com.web.project.repository.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropiedadService {

    @Autowired
    private PropiedadRepository propiedadRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    public List<PropiedadResponse> listarPropiedades() {
        return propiedadRepository.findAll().stream().map(this::convertirAResponse).toList();
    }

    public Propiedad obtenerPorId(Integer id) {
        return propiedadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Propiedad no encontrada"));
    }

    public void crearPropiedad(PropiedadDTO dto) {
        Propiedad propiedad = new Propiedad();
        propiedad.setCapacidad(dto.getCapacidad());
        propiedad.setDescripcion(dto.getDescripcion());
        propiedad.setDireccion(dto.getDireccion());
        propiedad.setNombre(dto.getNombre());
        propiedad.setPrecioPorNoche(dto.getPrecioPorNoche());

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        propiedad.setUsuario(usuario);

        List<Servicio> servicios = servicioRepository.findAllById(dto.getServiciosIds());
        propiedad.setServicios(servicios);

        propiedadRepository.save(propiedad);
    }

    public Propiedad actualizar(Integer id, PropiedadDTO dto) {
        Propiedad propiedad = obtenerPorId(id);
        propiedad.setNombre(dto.getNombre());
        propiedad.setDescripcion(dto.getDescripcion());
        propiedad.setDireccion(dto.getDireccion());
        propiedad.setCapacidad(dto.getCapacidad());
        propiedad.setPrecioPorNoche(dto.getPrecioPorNoche());

        return propiedadRepository.save(propiedad);
    }

    public void eliminar(Integer id) {
        propiedadRepository.deleteById(id);
    }

    public PropiedadResponse convertirAResponse(Propiedad propiedad) {
        UsuarioResumen usuario = new UsuarioResumen(
                propiedad.getUsuario().getId(),
                propiedad.getUsuario().getNombre(),
                propiedad.getUsuario().getEmail());

        List<ServicioResponse> servicios = propiedad.getServicios().stream()
                .map(servicio -> new ServicioResponse(
                        servicio.getId(),
                        servicio.getNombre()))
                .collect(Collectors.toList());

        return new PropiedadResponse(
                propiedad.getId(),
                propiedad.getNombre(),
                propiedad.getDireccion(),
                propiedad.getDescripcion(),
                propiedad.getCapacidad(),
                propiedad.getPrecioPorNoche(),
                usuario,
                servicios);
    }

}
