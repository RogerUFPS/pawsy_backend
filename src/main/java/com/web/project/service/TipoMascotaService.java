package com.web.project.service;

import com.web.project.dto.TipoMascotaReq;
import com.web.project.entity.TipoMascota;
import com.web.project.repository.TipoMascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TipoMascotaService {

    @Autowired
    private TipoMascotaRepository tipoMascotaRepository;

    public List<TipoMascota> listarTodos() {
        return tipoMascotaRepository.findAll();
    }

    public TipoMascota obtenerPorId(Integer id) {
        return tipoMascotaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("TipoMascota no encontrado con ID: " + id));
    }

    public TipoMascota guardar(TipoMascotaReq t) {
        TipoMascota f = new TipoMascota();
        f.setNombre(t.getNombre());
        return tipoMascotaRepository.save(f);
    }

    public TipoMascota actualizar(Integer id, TipoMascotaReq n) {
        TipoMascota existente = obtenerPorId(id);
        existente.setNombre(n.getNombre());
        return tipoMascotaRepository.save(existente);
    }

    public void eliminar(Integer id) {
        if (!tipoMascotaRepository.existsById(id)) {
            throw new NoSuchElementException("TipoMascota no encontrado con ID: " + id);
        }
        tipoMascotaRepository.deleteById(id);
    }
}

