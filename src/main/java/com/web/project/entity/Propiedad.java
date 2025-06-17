package com.web.project.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="propiedades")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Propiedad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false)
	private Integer capacidad;

	@Column(length=300)
	private String descripcion;

	@Column(nullable=false, length=200)
	private String direccion;

	@Column(nullable=false, length=100)
	private String nombre;

	@Column(name="precio_por_noche", nullable=false, precision=10, scale=2)
	private BigDecimal precioPorNoche;

	@OneToMany(mappedBy="propiedad", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<FotoPropiedad> fotos;

	@ManyToOne
	@JoinColumn(name="cuidador_id", nullable=false)
	private Usuario usuario;

	@OneToMany(mappedBy="propiedad", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Reserva> reservas;

	@ManyToMany(mappedBy="propiedades")
	private List<Servicio> servicios;
}

