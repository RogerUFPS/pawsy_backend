package com.web.project.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name="usuarios")
@AllArgsConstructor
@Data
@NoArgsConstructor	
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	// @Column(length=200)
	// private String direccion;
	
	@NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
	@Column(nullable=false, length=150)
	private String email;
	
	@NotBlank(message = "Una contraseña es obligatoria")
	@Column(nullable=false, length=100)
	private String clave;

	@NotBlank(message = "Debes identificarte con tu nombre")
	@Column(nullable=false, length=100)
	private String nombre;
	
	@Column(nullable=true, length=20)
	private String telefono;

	@NotBlank(message = "Debes designar si eres cuidador o cliente")
	@Column(name="tipo_usuario", nullable=false, length=20)

	private String tipoUsuario;

	@JsonIgnore
	@OneToMany(mappedBy="usuario")
	private List<Mascota> mascotas;

	@JsonIgnore
	@OneToMany(mappedBy="usuario")
	private List<Propiedad> propiedades;

    @Column(name = "token_verificacion", length = 100)
    private String token;

    @Column(name = "token_expiracion")
    private LocalDateTime expiracion;

    @Column(nullable = false)
    private boolean verificado = false;

	private List<String> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
        .collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return getClave();
	}

	@Override
	public String getUsername() {
		return getEmail();
	}


}
