package com.web.project.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class PropiedadDTO {

    private Integer capacidad;

    private String descripcion;

    private String direccion;

    private String nombre;

    private BigDecimal precioPorNoche;

    private List<FotosDtoRes> fotos;

    private List<Integer> serviciosId;
}
