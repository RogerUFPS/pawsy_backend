package com.web.project.dto;

import java.time.Instant;
import java.util.List;

import lombok.Data;

@Data
public class ReservaReq {
    public Instant fechaInicio;
    public Instant fechaFin;
    public Integer propiedadId;
    public List<Integer> serviciosId;
    public Integer idMascota;
}
