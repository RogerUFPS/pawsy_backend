package com.web.project.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the fotos_mascotas database table.
 * 
 */
@Data
@Entity
@Table(name="fotos_mascotas")
@AllArgsConstructor
@NoArgsConstructor
public class FotosMascota implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
