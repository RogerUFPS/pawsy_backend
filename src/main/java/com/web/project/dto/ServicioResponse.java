package com.web.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicioResponse {
    private Integer id;
    private String nombre;
    private String descripcion;
}