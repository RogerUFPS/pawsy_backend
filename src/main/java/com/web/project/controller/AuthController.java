package com.web.project.controller;

import com.web.project.dto.AuthRequest;
import com.web.project.dto.AuthResponse;
import com.web.project.dto.ChangePasswordRequest;
import com.web.project.dto.RegisterRequest;
import com.web.project.dto.RegisterResponse;
import com.web.project.dto.VerificationResponse;
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
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest req) {
        return ResponseEntity.ok(authService.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @GetMapping("/verificar")
    public ResponseEntity<VerificationResponse> verificar(@RequestParam String UUID, String email) {
        return ResponseEntity.ok(authService.verificar(UUID, email));
    }

    @PostMapping("/cambio-contra")
    public ResponseEntity<?> cambioContra(@RequestParam ChangePasswordRequest req) {
        authService.changePassword(req);
        return ResponseEntity.ok("La contraseña se actualizo correctamente");
    }
    
    
    
}