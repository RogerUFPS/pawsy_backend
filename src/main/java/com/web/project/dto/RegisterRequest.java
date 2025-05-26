package com.web.project.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email, password, nombre, tipoUsuario;

}