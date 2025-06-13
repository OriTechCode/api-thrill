package com.api.thrill.dto;

import com.api.thrill.entity.Direccion;
import com.api.thrill.entity.Imagen;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String username;
    private String email;
    private String rol;
    private Imagen imagenPerfil;
    private List<Direccion> direcciones;
    private Boolean eliminado;
}