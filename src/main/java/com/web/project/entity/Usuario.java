package com.web.project.entity;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;	
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name="usuarios")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
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

	@Column(nullable=false, length=150)
	private String email;

	@Column(nullable=false, length=100)
	private String nombre;

	@Column(length=20)
	private String telefono;

	@Column(name="tipo_usuario", nullable=false, length=20)
	private String tipoUsuario;

	//bi-directional many-to-one association to Mascota
	@OneToMany(mappedBy="usuario")
	private List<Mascota> mascotas;

	//bi-directional many-to-one association to Propiedade
	@OneToMany(mappedBy="usuario")
	private List<Propiedad> propiedades;


}
