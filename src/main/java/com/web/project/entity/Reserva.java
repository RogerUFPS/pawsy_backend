package com.web.project.entity;

import java.io.Serializable;
import java.time.Instant;
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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false, length=20)
	private String estado;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_fin", nullable=false)
	private Instant fechaFin;

	@Column(name="fecha_inicio", nullable=false)
	private Instant fechaInicio;

	@ManyToOne
	@JoinColumn(name="mascota_id", nullable=false)
	private Mascota mascota;

	@ManyToOne
	@JoinColumn(name="propiedad_id", nullable=false)
	private Propiedad propiedad;

	@ManyToMany(mappedBy="reservas")
	private List<Servicio> servicios;

}
