package com.web.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.web.project.dto.ReservaReq;
import com.web.project.entity.Mascota;
import com.web.project.entity.Propiedad;
import com.web.project.entity.Reserva;
import com.web.project.entity.Servicio;
import com.web.project.entity.Usuario;
import com.web.project.repository.MascotaRepository;
import com.web.project.repository.PropiedadRepository;
import com.web.project.repository.ReservaRepository;
import com.web.project.repository.ServicioRepository;
import com.web.project.repository.UsuarioRepository;

@Service
public class ReservaService {
    
    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PropiedadRepository propiedadRepository;
    @Autowired
    private ServicioRepository servicioRepository;
    @Autowired
    private MascotaRepository mascotaRepository;

    public void reservar(ReservaReq resq) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario a = usuarioRepository.findByEmail(authentication.getName()).orElseThrow(()->new RuntimeException("El usuario no existe"));
        Propiedad p = propiedadRepository.findById(resq.getPropiedadId()).orElseThrow(()-> new RuntimeException("La propiedad no existe"));
        if(p.getCapacidad() == 0) throw new RuntimeException("Esta propiedad ya no tiene mas cupos");

        Mascota m = mascotaRepository.findById(resq.getIdMascota()).orElseThrow(()-> new RuntimeException("La mascota no existe"));

        List<Servicio> lS = new ArrayList<>();
        for(Integer i : resq.getServiciosId()) {
            Servicio nT = new Servicio();
            nT = servicioRepository.findById(i).orElseThrow(()-> new RuntimeException("El servicio no existe"));
            lS.add(nT);
        }
        Reserva r = new Reserva();
        r.setEstado("Activa");
        r.setFechaInicio(resq.getFechaInicio());
        r.setFechaFin(resq.getFechaFin());
        r.setMascota(m);
        r.setPropiedad(p);
        r.setServicios(lS);
        reservaRepository.save(r);
    }

}
