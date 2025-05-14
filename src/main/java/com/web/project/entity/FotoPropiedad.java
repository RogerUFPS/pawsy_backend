package com.web.project.entity;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;	

/**
 * The persistent class for the fotos_propiedades database table.
 * 
 */
@Data
@Entity
@Table(name="fotos_propiedades")
@NamedQuery(name="FotosPropiedade.findAll", query="SELECT f FROM FotosPropiedade f")
@NoArgsConstructor
public class FotoPropiedad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FOTOS_PROPIEDADES_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FOTOS_PROPIEDADES_ID_GENERATOR")
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(length=200)
	private String descripcion;

	@Column(nullable=false, length=300)
	private String url;

	//bi-directional many-to-one association to Propiedade
	@ManyToOne
	@JoinColumn(name="propiedad_id", nullable=false)
	private Propiedad propiedade;

}
