package com.web.project.entity;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;	

@Data
@Entity
@Table(name="fotos_propiedades")
@NoArgsConstructor
@AllArgsConstructor
public class FotoPropiedad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(length=200)
	private String descripcion;

	@Column(nullable=false, length=300)
	private String url;

	//bi-directional many-to-one association to Propiedade
	@ManyToOne
	@JoinColumn(name="propiedad_id", nullable=false)
	private Propiedad propiedad;

}
