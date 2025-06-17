package com.web.project.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MascotaDTO {

    private String nombre;

    private String descripcion;

    private Integer edad;

    private Integer tipoId;
}
