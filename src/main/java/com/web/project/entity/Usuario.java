package com.web.project.entity;

<<<<<<< HEAD
=======
import java.io.Serializable;
>>>>>>> 661b8442d2d6ea78fb5c0006aa4ecc5e30213384
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
<<<<<<< HEAD
import org.springframework.security.core.authority.SimpleGrantedAuthority;
=======
>>>>>>> 661b8442d2d6ea78fb5c0006aa4ecc5e30213384
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
<<<<<<< HEAD
public class Usuario implements UserDetails {
=======
public class Usuario implements Serializable, UserDetails {
>>>>>>> 661b8442d2d6ea78fb5c0006aa4ecc5e30213384
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(length=200)
	private String direccion;
	
	@NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
	@Column(nullable=false, length=150)
	private String email;
	
	@NotBlank(message = "Una contraseña es obligatoria")
<<<<<<< HEAD
	@Column(nullable=false, length=100)
=======
	@Column(nullable=false, length=20)
>>>>>>> 661b8442d2d6ea78fb5c0006aa4ecc5e30213384
	private String clave;

	@NotBlank(message = "Debes identificarte con tu nombre")
	@Column(nullable=false, length=100)
	private String nombre;
	
<<<<<<< HEAD
	@Column(nullable=true, length=20)
=======
	@Column(nullable=false, length=20)
>>>>>>> 661b8442d2d6ea78fb5c0006aa4ecc5e30213384
	private String telefono;

	@NotBlank(message = "Debes designar si eres cuidador o cliente")
	@Column(name="tipo_usuario", nullable=false, length=20)
<<<<<<< HEAD
	@Enumerated(EnumType.STRING)
=======
>>>>>>> 661b8442d2d6ea78fb5c0006aa4ecc5e30213384
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
<<<<<<< HEAD
		return List.of(new SimpleGrantedAuthority("ROLE_" + tipoUsuario));
=======
		return null;
>>>>>>> 661b8442d2d6ea78fb5c0006aa4ecc5e30213384
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
