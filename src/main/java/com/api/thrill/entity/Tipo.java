package com.api.thrill.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "tipos")
public class Tipo extends Base {


    private String nombre;

    @OneToMany(mappedBy = "tipo")
    @JsonManagedReference
    private List<Talle> talles;

}
