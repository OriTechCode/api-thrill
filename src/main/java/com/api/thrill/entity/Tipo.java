package com.api.thrill.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tipos")
public class Tipo extends Base {


    private String nombre;

    @OneToMany(mappedBy = "tipo")
    private List<Talle> talles;

}
