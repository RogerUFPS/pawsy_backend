package com.web.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ServiceEmail {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmailVerificacion(String to, String token) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(to);
        mensaje.setSubject("Verifica tu cuenta");
        mensaje.setText("Haz clic aquí para verificar: http://tusitio.com/verificar?token=" + token);
        mailSender.send(mensaje);
    }
}
