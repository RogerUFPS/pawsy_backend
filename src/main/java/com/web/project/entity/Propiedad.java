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
@Data
@NoArgsConstructor
public class Propiedad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PROPIEDADES_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PROPIEDADES_ID_GENERATOR")
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

	//bi-directional many-to-one association to FotosPropiedade
	@JsonIgnore
	@OneToMany(mappedBy="propiedade")
	private List<FotoPropiedad> fotosPropiedades;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="cuidador_id", nullable=false)
	private Usuario usuario;

	//bi-directional many-to-one association to Reserva
	@JsonIgnore
	@OneToMany(mappedBy="propiedade")
	private List<Reserva> reservas;

	//bi-directional many-to-many association to Servicio
	@ManyToMany(mappedBy="propiedades")
	private List<Servicio> servicios;

}
