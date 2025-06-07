package com.api.thrill.controller;

import com.api.thrill.entity.SubCategoria;
import com.api.thrill.service.SubCategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subcategorias")
public class SubCategoriaController extends BaseController<SubCategoria, Long> {

    private final SubCategoriaService subCategoriaService;

    public SubCategoriaController(SubCategoriaService subCategoriaService) {
        super(subCategoriaService);
        this.subCategoriaService = subCategoriaService;
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<SubCategoria>> findByCategoriaId(@PathVariable Long categoriaId) {

        return ResponseEntity.ok(subCategoriaService.findByCategoriaId(categoriaId));
    }
}