package com.api.thrill.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Imagen extends Base {

    private String url;
    private String publicId;  // indentificador para Cloudinary

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
}