package com.web.project.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="reservas")
@AllArgsConstructor
@Data	
@NoArgsConstructor
public class Reserva implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="RESERVAS_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RESERVAS_ID_GENERATOR")
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false, length=20)
	private String estado;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_fin", nullable=false)
	private Date fechaFin;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_inicio", nullable=false)
	private Date fechaInicio;

	//bi-directional many-to-one association to Mascota
	@ManyToOne
	@JoinColumn(name="mascota_id", nullable=false)
	private Mascota mascota;

	//bi-directional many-to-one association to Propiedade
	@ManyToOne
	@JoinColumn(name="propiedad_id", nullable=false)
	private Propiedad propiedad;

	//bi-directional many-to-many association to Servicio
	@ManyToMany(mappedBy="reservas")
	private List<Servicio> servicios;


}
