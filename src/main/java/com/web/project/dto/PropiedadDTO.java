package com.web.project.dto;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropiedadDTO {

    @NotNull(message = "La capacidad es obligatoria")
    @Min(value = 1, message = "La capacidad mínima es 1")
    private Integer capacidad;

    @Size(max = 300, message = "La descripción debe tener máximo 300 caracteres")
    private String descripcion;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 200, message = "La dirección debe tener máximo 200 caracteres")
    private String direccion;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre debe tener máximo 100 caracteres")
    private String nombre;

    private BigDecimal precioPorNoche;

    //@NotNull(message = "El ID del usuario es obligatorio")
    //private Integer usuarioId;

    private List<Integer> serviciosIds;
}
