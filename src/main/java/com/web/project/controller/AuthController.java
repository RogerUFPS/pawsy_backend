package com.web.project.controller;

import com.web.project.dto.AuthRequest;
import com.web.project.dto.AuthResponse;
import com.web.project.dto.ChangePasswordRequest;
import com.web.project.dto.RegisterRequest;
import com.web.project.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) { this.authService = authService; }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        return authService.register(req);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @GetMapping("/verificar-email")
    public ResponseEntity<?> verificar(@RequestParam String UUID, @RequestParam String email) {
        authService.verificarEmail(UUID, email);
        return ResponseEntity.ok("Se ha verificado su email");
    }

    @PostMapping("/reenvio-token")
    public ResponseEntity<?> reenviarToken(@RequestParam String email) {
        
        authService.reenviarToken(email);
        return ResponseEntity.ok("Se ha reenviado el token, revisa el correo");
    }
    

    @PostMapping("/cambio-contra")
    public ResponseEntity<?> cambioContra(@RequestBody ChangePasswordRequest req) {
        authService.changePassword(req);
        return ResponseEntity.ok("La contraseña se actualizo correctamente");
    }

    @PostMapping("/recuperar-sent-token")
    public ResponseEntity<?> recuperarContra(@RequestBody String email) {
        authService.recoverPassword(email);
        return ResponseEntity.ok("Se ha enviado el correo de verificacion");
    }

    @PostMapping("/verificar-cambio-contra")
    public ResponseEntity<?> verificarCambioContra(@RequestParam String UUID, @RequestParam String email, @RequestBody String contraNueva) {
        
        authService.confirmarRecuperacion(email, UUID, contraNueva);
        return ResponseEntity.ok("Se ha cambiado la contraseña");
    }
    
    
    
    
    
}