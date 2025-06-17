package com.web.project.entity;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="servicios")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Servicio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(length=300)
	private String descripcion;

	@Column(nullable=false, length=100)
	private String nombre;

	//bi-directional many-to-many association to Propiedade
	@ManyToMany
	@JoinTable(
		name="servicios_propiedades"
		, joinColumns={
			@JoinColumn(name="servicio_id", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="propiedad_id", nullable=false)
			}
		)
	private List<Propiedad> propiedades;

	//bi-directional many-to-many association to Reserva
	@ManyToMany
	@JoinTable(
		name="servicios_reservas"
		, joinColumns={
			@JoinColumn(name="servicio_id", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="reserva_id", nullable=false)
			}
		)
	private List<Reserva> reservas;

}
