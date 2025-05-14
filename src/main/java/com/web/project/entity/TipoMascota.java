package com.web.project.entity;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tipo_mascota")
@NamedQuery(name="TipoMascota.findAll", query="SELECT t FROM TipoMascota t")
@Data
@NoArgsConstructor 
public class TipoMascota implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIPO_MASCOTA_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TIPO_MASCOTA_ID_GENERATOR")
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false, length=50)
	private String nombre;

	//bi-directional many-to-one association to Mascota
	@OneToMany(mappedBy="tipoMascota")
	private List<Mascota> mascotas;


}
