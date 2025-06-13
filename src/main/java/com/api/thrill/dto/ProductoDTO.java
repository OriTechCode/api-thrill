package com.api.thrill.dto;


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
public class ProductoDTO extends Producto{

    private String nombre;
    private String descripcion;
    private String marca;
    private Double precio;
    private String color;

    private List<String> imagenesUrls;



}
