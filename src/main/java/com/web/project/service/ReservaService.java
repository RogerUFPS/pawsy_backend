package com.web.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.web.project.dto.ReservaReq;
import com.web.project.entity.Usuario;
import com.web.project.repository.ReservaRepository;
import com.web.project.repository.UsuarioRepository;

@Service
public class ReservaService {
    
    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public void reservar(ReservaReq resq) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario a = usuarioRepository.findByEmail(authentication.getName()).orElseThrow(()->new RuntimeException("El usuario no existe"));
    }

}
