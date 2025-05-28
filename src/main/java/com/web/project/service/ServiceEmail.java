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
<<<<<<< HEAD
        mensaje.setText("Haz clic aquí para verificar: http://localhost:8081/auth/verificar-email?UUID=" + token + "&email="+to);
        mailSender.send(mensaje);
    }

    public void recuperarContra(String email, String token) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(email);
        mensaje.setSubject("Recupera tu contraseña");
        mensaje.setText("Haz clic aqui para cambiar tu contra: http://localhost:8081/auth/cambio-contra?UUID="+token +"&email="+email);
        mailSender.send(mensaje);
    }

=======
        mensaje.setText("Haz clic aquí para verificar: http://tusitio.com/verificar?token=" + token);
        mailSender.send(mensaje);
    }
>>>>>>> 661b8442d2d6ea78fb5c0006aa4ecc5e30213384
}
