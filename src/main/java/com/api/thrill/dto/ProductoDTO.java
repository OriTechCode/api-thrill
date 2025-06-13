package com.api.thrill.dto;


import com.api.thrill.entity.Categoria;
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
    private List<Categoria> categorias;
    private List<String> imagenesUrls;



}
