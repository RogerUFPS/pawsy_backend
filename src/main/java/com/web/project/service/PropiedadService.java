package com.web.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.web.project.dto.PropiedadDTO;
import com.web.project.entity.Propiedad;
import com.web.project.entity.Servicio;
import com.web.project.entity.Usuario;
import com.web.project.repository.PropiedadRepository;
import com.web.project.repository.ServicioRepository;
import com.web.project.repository.UsuarioRepository;

import java.util.List;

@Service
public class PropiedadService {

    @Autowired
    private PropiedadRepository propiedadRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    public List<Propiedad> listarTodas() {
        return propiedadRepository.findAll();
    }

    public Propiedad obtenerPorId(Integer id) {
        return propiedadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Propiedad no encontrada"));
    }

    //Peticion realizada cuando se logeo
    public void crearPropiedad(PropiedadDTO dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!usuarioRepository.findByEmail(authentication.getName()).isPresent()){
            throw new RuntimeException("El usuario no existe");    
        }
        Usuario u = usuarioRepository.findByEmail(authentication.getName()).get();

        Propiedad propiedad = new Propiedad();
        propiedad.setCapacidad(dto.getCapacidad());
        propiedad.setDescripcion(dto.getDescripcion());
        propiedad.setDireccion(dto.getDireccion());
        propiedad.setNombre(dto.getNombre());
        propiedad.setPrecioPorNoche(dto.getPrecioPorNoche());

        propiedad.setUsuario(u);
        List<Servicio> servicios = servicioRepository.findAllById(dto.getServiciosIds());
        propiedad.setServicios(servicios);

        propiedadRepository.save(propiedad);
    }

    //Peticion realizada cuando se logea
    public Propiedad actualizar(Integer id, PropiedadDTO propiedadActualizada) {
        Propiedad propiedad = obtenerPorId(id);
        propiedad.setNombre(propiedadActualizada.getNombre());
        propiedad.setDescripcion(propiedadActualizada.getDescripcion());
        propiedad.setDireccion(propiedadActualizada.getDireccion());
        propiedad.setCapacidad(propiedadActualizada.getCapacidad());
        propiedad.setPrecioPorNoche(propiedadActualizada.getPrecioPorNoche());

        return propiedadRepository.save(propiedad);
    }

    public void eliminar(Integer id) {
        propiedadRepository.deleteById(id);
    }
}
