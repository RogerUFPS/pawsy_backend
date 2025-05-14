package com.web.project.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MascotaDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer edad;
    private Integer clienteId;
    private Integer tipoId;
}
