package com.web.project.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class PropiedadRes {
    private int id;
    private int capacidad;
    private String descripcion;
    private String nombre;
    private BigDecimal precioPorNoche;
    private List<FotosDtoRes> fotos;
    private UsuarioProfile usuario;
}
