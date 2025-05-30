package com.web.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "USUARIOS_ID_GENERATOR", sequenceName = "usuarios_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIOS_ID_GENERATOR")
    @Column(unique = true, nullable = false)
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "La clave es obligatoria")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String clave;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    @Size(max = 150)
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String telefono;

    @Size(max = 200)
    @Column(length = 200)
    private String direccion;

    @NotBlank(message = "El tipo de usuario es obligatorio")
    @Column(name = "tipo_usuario", nullable = false, length = 20)
    private String tipoUsuario;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mascota> mascotas;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Propiedad> propiedades;
}
