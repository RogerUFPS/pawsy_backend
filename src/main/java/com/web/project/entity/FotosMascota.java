package com.web.project.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the fotos_mascotas database table.
 * 
 */
@Data
@Entity
@Table(name="fotos_mascotas")
@NamedQuery(name="FotosMascota.findAll", query="SELECT f FROM FotosMascota f")
@NoArgsConstructor
public class FotosMascota implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FOTOS_MASCOTAS_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FOTOS_MASCOTAS_ID_GENERATOR")
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(length=200)
	private String descripcion;

	@Column(nullable=false, length=300)
	private String url;

	//bi-directional many-to-one association to Mascota
	@ManyToOne
	@JoinColumn(name="mascota_id", nullable=false)
	private Mascota mascota;

}
