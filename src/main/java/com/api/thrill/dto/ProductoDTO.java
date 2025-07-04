package com.api.thrill.dto;


import com.api.thrill.entity.Categoria;
import com.api.thrill.entity.Imagen;
import com.api.thrill.entity.Tipo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.api.thrill.entity.Producto;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {
    private String nombre;
    private String descripcion;
    private String marca;
    private Double precio;
    private String color;
    private List<Long> categoriaIds;  // Ya está correcto
    private Long tipoId;  // Cambiar de "tipoid" a "tipoId" y de "long" a "Long"
    private List<String> imagenes;  // Ya está correcto
}