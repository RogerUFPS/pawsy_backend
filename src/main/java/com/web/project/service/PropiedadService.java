package com.web.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.web.project.dto.FotosDtoRes;
import com.web.project.dto.PropiedadDTO;
import com.web.project.dto.PropiedadRes;
import com.web.project.dto.UsuarioProfile;
import com.web.project.entity.FotoPropiedad;
import com.web.project.entity.Propiedad;
import com.web.project.entity.Servicio;
import com.web.project.entity.Usuario;
import com.web.project.repository.PropiedadRepository;
import com.web.project.repository.ServicioRepository;
import com.web.project.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PropiedadService {

    @Autowired
    private PropiedadRepository propiedadRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    public List<PropiedadRes> listarTodas() {
        List<PropiedadRes> pdr = new ArrayList<>();
        for(Propiedad p : propiedadRepository.findAll()){
            PropiedadRes prN = new PropiedadRes();
            prN.setCapacidad(p.getCapacidad());
            prN.setDescripcion(p.getDescripcion());
            prN.setId(p.getId());
            prN.setNombre(p.getNombre());
            prN.setPrecioPorNoche(p.getPrecioPorNoche());

            UsuarioProfile usp = new UsuarioProfile();
            usp.setEmail(p.getUsuario().getEmail());
            usp.setNombre(p.getUsuario().getNombre());
            usp.setTelefono(p.getUsuario().getTelefono());
            usp.setTipoUsuario(p.getUsuario().getTipoUsuario());
            prN.setUsuario(usp);
            
            List<FotosDtoRes> fot = new ArrayList<>();
            for(FotoPropiedad fp: p.getFotos()) {
                FotosDtoRes nFr = new FotosDtoRes();
                nFr.setDescripcion(fp.getDescripcion());
                nFr.setUrl(fp.getUrl());
                fot.add(nFr); 
            }
            prN.setFotos(fot);
            pdr.add(prN);

        }
        return pdr;
    }

    public List<PropiedadRes> obtenerPropiedadesCuidador() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario u = usuarioRepository.findByEmail(authentication.getName()).orElseThrow(()-> new RuntimeException("No existe el usuario"));
        List<PropiedadRes> lpR = new ArrayList<>();
        for(Propiedad p : u.getPropiedades()) {
            PropiedadRes prN = new PropiedadRes();
            prN.setCapacidad(p.getCapacidad());
            prN.setDescripcion(p.getDescripcion());
            prN.setId(p.getId());
            prN.setNombre(p.getNombre());
            prN.setPrecioPorNoche(p.getPrecioPorNoche());

            UsuarioProfile usp = new UsuarioProfile();
            usp.setEmail(p.getUsuario().getEmail());
            usp.setNombre(p.getUsuario().getNombre());
            usp.setTelefono(p.getUsuario().getTelefono());
            usp.setTipoUsuario(p.getUsuario().getTipoUsuario());
            prN.setUsuario(usp);
            
            List<FotosDtoRes> fot = new ArrayList<>();
            for(FotoPropiedad fp: p.getFotos()) {
                FotosDtoRes nFr = new FotosDtoRes();
                nFr.setDescripcion(fp.getDescripcion());
                nFr.setUrl(fp.getUrl());
                fot.add(nFr); 
            }
            prN.setFotos(fot);
            lpR.add(prN);
        }
        return lpR;
    }

    public void crearPropiedad(PropiedadDTO dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario u = usuarioRepository.findByEmail(authentication.getName()).orElseThrow(()-> new RuntimeException("El usuario no existe"));

        Propiedad propiedad = new Propiedad();
        propiedad.setCapacidad(dto.getCapacidad());
        propiedad.setDescripcion(dto.getDescripcion());
        propiedad.setDireccion(dto.getDireccion());
        propiedad.setNombre(dto.getNombre());
        propiedad.setPrecioPorNoche(dto.getPrecioPorNoche());
        propiedad.setUsuario(u);

        List<FotoPropiedad> fotP = new ArrayList<>();
        for(FotosDtoRes fdr : dto.getFotos()) {
            FotoPropiedad fot = new FotoPropiedad();
            fot.setDescripcion(fdr.getDescripcion());
            fot.setUrl(fdr.getUrl());
            fot.setPropiedad(propiedad);
            fotP.add(fot);
        }

        List<Servicio> serv = new ArrayList<>();
        for(int s : dto.getServiciosId()) {
            Servicio x = servicioRepository.findById(s).orElseThrow(()-> new RuntimeException("El servicio no existe"));
            serv.add(x);
        }
        propiedad.setServicios(serv);
        propiedadRepository.save(propiedad);
    }

    public void actualizar(PropiedadDTO propiedadActualizada, int idPropiedad) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario u = usuarioRepository.findByEmail(authentication.getName()).orElseThrow(()-> new RuntimeException("El usuario no existe"));
        Propiedad p = propiedadRepository.findById(idPropiedad).orElseThrow(()-> new RuntimeException("La propiedad no existe"));
        if(!u.getPropiedades().contains(p)) throw new RuntimeException("La propiedad no existe");

        p.setNombre(propiedadActualizada.getNombre());
        p.setDescripcion(propiedadActualizada.getDescripcion());
        p.setDireccion(propiedadActualizada.getDireccion());
        p.setCapacidad(propiedadActualizada.getCapacidad());
        p.setPrecioPorNoche(propiedadActualizada.getPrecioPorNoche());
        propiedadRepository.save(p);
    }

    public void eliminar(Integer idPropiedad) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario u = usuarioRepository.findByEmail(authentication.getName()).orElseThrow(()-> new RuntimeException("El usuario no existe"));
        Propiedad p = propiedadRepository.findById(idPropiedad).orElseThrow(()-> new RuntimeException("La propiedad no existe"));
        if(!u.getPropiedades().contains(p)) throw new RuntimeException("La propiedad no existe");
        propiedadRepository.delete(p);
    }
}
