package com.web.project.service;

import com.web.project.dto.AuthRequest;
import com.web.project.dto.AuthResponse;
import com.web.project.dto.ChangePasswordRequest;
import com.web.project.dto.RegisterRequest;
import com.web.project.dto.RegisterResponse;
<<<<<<< HEAD
=======
import com.web.project.dto.VerificationResponse;
>>>>>>> 661b8442d2d6ea78fb5c0006aa4ecc5e30213384
import com.web.project.entity.Usuario;
import com.web.project.repository.UsuarioRepository;

import java.time.LocalDateTime;
<<<<<<< HEAD
import java.util.UUID;

=======
import java.util.Optional;
import java.util.UUID;

import javax.naming.AuthenticationException;
>>>>>>> 661b8442d2d6ea78fb5c0006aa4ecc5e30213384

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

        Usuario usuario = usuarioRepo.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Usuario no existente"));

        if(!usuario.isVerificado()) {
            throw new IllegalStateException("Debes verificar tu correo antes de iniciar sesion");
        }

        try {
            authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch(RuntimeException ex) {throw new RuntimeException("Credenciales invalidas");}

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token);
    }

    public RegisterResponse register(RegisterRequest request) {

        if (usuarioRepo.findByEmail(request.getEmail()).isPresent()) {

            Usuario ex = usuarioRepo.findByEmail(request.getEmail()).get();

            if(!ex.isVerificado()) {

                if(ex.getExpiracion().isBefore(LocalDateTime.now())) {
                    usuarioRepo.delete(ex);
                } else {
                    throw new RuntimeException("Ya existe el usuario, esta pendiente su verificacion");
                }
            } else {
                throw new RuntimeException("El usuario ya existe.");
            }
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

<<<<<<< HEAD
    public void verificarEmail(String UUID, String email) {
=======
    public VerificationResponse verificar(String UUID, String email) {
>>>>>>> 661b8442d2d6ea78fb5c0006aa4ecc5e30213384
        
        if(!usuarioRepo.findByEmail(email).isPresent()) {
            throw new RuntimeException("El usuario no existe");    
        } 
        Usuario s = usuarioRepo.findByEmail(email).get();

        if(s.getExpiracion().isBefore(LocalDateTime.now() ) ) {
            usuarioRepo.delete(s);
            throw new RuntimeException("El tiempo de expiracion del token ya pasó!"); 
        }
<<<<<<< HEAD

        if(!s.getToken().equals(UUID)) {

        }

        s.setVerificado(true);
        usuarioRepo.save(s);
=======
        s.setVerificado(true);
        usuarioRepo.save(s);
        return new VerificationResponse(UUID);
>>>>>>> 661b8442d2d6ea78fb5c0006aa4ecc5e30213384
    }

    public void changePassword(ChangePasswordRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        //Es el email
        String email = authentication.getName();
    
        Usuario usuario = usuarioRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        if(!passwordEncoder.matches(request.getCurrentPassword(), usuario.getClave())) {
            throw new IllegalArgumentException("La contraseña es incorrecta");
        }

        usuario.setClave(passwordEncoder.encode(request.getNewPassword()));
        usuarioRepo.save(usuario);
    }

<<<<<<< HEAD
    public void recoverPassword(String email) {

        Usuario usuario = usuarioRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("El usuario no existe"));

        if(!usuario.isVerificado()){
            throw new IllegalStateException("El usuario no esta verificado, no puede recuperar su contraseña");
        }

        usuario.setToken(UUID.randomUUID().toString());
        usuarioRepo.save(usuario);
        em.recuperarContra(email, usuario.getToken());
    }

    public void confirmarRecuperacion(String email, String UUID, String nuevaContra) {

        Usuario usuario = usuarioRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("El usuario no existe"));

        if(!usuario.getToken().equals(UUID)) {
            throw new IllegalArgumentException("Incorrecto, verificacion invalida");
        }

        usuario.setClave(passwordEncoder.encode(nuevaContra));
        usuario.setToken(null);
        usuarioRepo.save(usuario);
    }

=======
>>>>>>> 661b8442d2d6ea78fb5c0006aa4ecc5e30213384
    public void reenviarToken(String email) {

        Usuario usuario = usuarioRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        if(usuario.isVerificado()) {
            throw new IllegalStateException("El usuario ya esta verificado");
        }

        usuario.setToken(UUID.randomUUID().toString());
        usuario.setExpiracion(LocalDateTime.now().plusMinutes(5));
        usuarioRepo.save(usuario);
        em.enviarEmailVerificacion(email, usuario.getToken());

    }
}
