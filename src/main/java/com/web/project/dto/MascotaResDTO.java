package com.web.project.dto;

import java.util.List;

import lombok.Data;

@Data
public class MascotaResDTO {
    private int id;
    private String nombre;
    private String descripcion;
    private Integer edad;
    private Integer tipoId;
    private List<FotosDtoRes> fotos;
}
