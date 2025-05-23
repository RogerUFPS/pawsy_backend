package com.web.project.service;

import com.web.project.dto.AuthRequest;
import com.web.project.dto.AuthResponse;
import com.web.project.dto.ChangePasswordRequest;
import com.web.project.dto.RegisterRequest;
import com.web.project.dto.RegisterResponse;
import com.web.project.dto.VerificationResponse;
import com.web.project.entity.Usuario;
import com.web.project.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private ServiceEmail em;
    
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final UsuarioRepository usuarioRepo;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public AuthService(AuthenticationManager authManager,
                       UsuarioRepository usuarioRepo,
                       JwtService jwtService,
                       UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.authManager = authManager;
        this.usuarioRepo = usuarioRepo;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse login(AuthRequest request) {
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token);
    }

    public RegisterResponse register(RegisterRequest request) {
        if (usuarioRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("El usuario ya existe.");
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(request.getNombre());
        nuevoUsuario.setEmail(request.getEmail());
        nuevoUsuario.setTipoUsuario(request.getTipoUsuario());
        nuevoUsuario.setClave(passwordEncoder.encode(request.getPassword()));
        nuevoUsuario.setVerificado(false);

        String uuid = UUID.randomUUID().toString();
        nuevoUsuario.setToken(uuid);
        nuevoUsuario.setExpiracion(LocalDateTime.now().plusMinutes(5));

        em.enviarEmailVerificacion(nuevoUsuario.getEmail(), nuevoUsuario.getToken());

        usuarioRepo.save(nuevoUsuario);

        String token = jwtService.generateToken(nuevoUsuario);
        return new RegisterResponse(token);
    }

    public VerificationResponse verificar(String UUID, String email) {
        
        if(!usuarioRepo.findByEmail(email).isPresent()) {
            throw new RuntimeException("El usuario no existe");    
        } 
        Usuario s = usuarioRepo.findByEmail(email).get();

        if(s.getExpiracion().isAfter(LocalDateTime.now().plusMinutes(5) ) ) {
            throw new RuntimeException("El tiempo de expiracion del token ya pasó!"); 
        }
        s.setVerificado(true);
        usuarioRepo.save(s);
        return new VerificationResponse(UUID);
    }

    public void changePassword(ChangePasswordRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        //Es el email
        String email = authentication.getName();
    
        Usuario usuario = usuarioRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        if(!passwordEncoder.matches(request.getCurrentPassword(), usuario.getClave())) {
            throw new IllegalArgumentException("La contraseña es incorrecta");
        }

        usuario.setClave(request.getNewPassword());
        usuarioRepo.save(usuario);
    }
}
