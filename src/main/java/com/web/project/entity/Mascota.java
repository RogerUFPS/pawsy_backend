package com.web.project.entity;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="mascotas")
@NamedQuery(name="Mascota.findAll", query="SELECT m FROM Mascota m")
@Data
@NoArgsConstructor
public class Mascota implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MASCOTAS_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MASCOTAS_ID_GENERATOR")
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(length=300)
	private String descripcion;

	private Integer edad;

	@Column(nullable=false, length=100)
	private String nombre;

	//bi-directional many-to-one association to FotosMascota
	@OneToMany(mappedBy="mascota")
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
	@OneToMany(mappedBy="mascota")
	private List<Reserva> reservas;

}
