package com.web.project.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

import com.web.project.entity.Propiedad;

@Data
@Builder
public class CuidadorDTO {

    Integer id;

    private String nombre;

    private String email;

    private String telefono;

    private List<Propiedad> propiedades;
}
