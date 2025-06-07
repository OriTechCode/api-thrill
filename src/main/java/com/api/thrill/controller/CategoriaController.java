package com.api.thrill.controller;

import com.api.thrill.entity.Categoria;
import com.api.thrill.service.CategoriaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController extends BaseController<Categoria, Long> {

    public CategoriaController(CategoriaService categoriaService) {
        super(categoriaService); // Pasamos el servicio al constructor de BaseController
    }
}