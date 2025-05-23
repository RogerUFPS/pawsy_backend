package com.web.project.entity;

import java.io.Serializable;
import java.util.List;

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
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USUARIOS_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USUARIOS_ID_GENERATOR")
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(length=200)
	private String direccion;
	
	@NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
	@Column(nullable=false, length=150)
	private String email;
	
	@NotBlank(message = "Una contraseña es obligatoria")
	@Column(nullable=false, length=20)
	private String clave;

	@Column(nullable=false, length=100)
	private String nombre;
	
	@NotBlank(message = "El telefono es obligatorio")
	@Column(nullable=false, length=20)
	private String telefono;

	@Column(name="tipo_usuario", nullable=false, length=20)
	private String tipoUsuario;

	@JsonIgnore
	@OneToMany(mappedBy="usuario")
	private List<Mascota> mascotas;

	@JsonIgnore
	@OneToMany(mappedBy="usuario")
	private List<Propiedad> propiedades;


}
