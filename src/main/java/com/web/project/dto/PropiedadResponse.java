package com.web.project.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropiedadResponse {
    private Integer id;
    private String nombre;
    private String direccion;
    private String descripcion;
    private Integer capacidad;
    private BigDecimal precioPorNoche;
    private UsuarioResumen usuario;
    private List<ServicioResponse> servicios;
}
