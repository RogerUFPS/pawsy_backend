package com.web.project.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tipo_mascota")
@AllArgsConstructor
@Data
@NoArgsConstructor 
public class TipoMascota implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false, length=50)
	private String nombre;

	//bi-directional many-to-one association to Mascota
	@JsonIgnore
	@OneToMany(mappedBy="tipoMascota")
	private List<Mascota> mascotas;


}
