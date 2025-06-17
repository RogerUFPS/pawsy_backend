package com.web.project.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="mascotas")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Mascota implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(length=300)
	private String descripcion;

	private Integer edad;

	@Column(nullable=false, length=100)
	private String nombre;

	//bi-directional many-to-one association to FotosMascota
	@JsonIgnore
	@OneToMany(mappedBy="mascota", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FotosMascota> fotosMascotas;

	//bi-directional many-to-one association to TipoMascota
	@ManyToOne
	@JoinColumn(name="tipo_id")
	private TipoMascota tipoMascota;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="cliente_id", nullable=false)
	private Usuario usuario;

	//bi-directional many-to-one association to Reserva
	@JsonIgnore
	@OneToMany(mappedBy="mascota", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Reserva> reservas;

}
