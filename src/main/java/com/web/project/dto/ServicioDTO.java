package com.web.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicioDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Size(max = 300, message = "La descripción debe tener máximo 300 caracteres")
    private String descripcion;
}