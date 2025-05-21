package com.api.thrill.controller;


import com.api.thrill.entity.SubCategoria;
import com.api.thrill.service.SubCategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subcategorias")
public class SubCategoriaController {

    @Autowired
    private SubCategoriaService subCategoriaService;

    @GetMapping("")
    public ResponseEntity<List<SubCategoria>> findAll() {
        return ResponseEntity.ok(subCategoriaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubCategoria> findById(@PathVariable Long id) {
        return ResponseEntity.ok(subCategoriaService.findById(id));
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<SubCategoria>> findByCategoriaId(@PathVariable Long categoriaId) {
        return ResponseEntity.ok(subCategoriaService.findByCategoriaId(categoriaId));
    }

    @PostMapping("")
    public ResponseEntity<SubCategoria> save(@RequestBody SubCategoria subCategoria) {
        return new ResponseEntity<>(subCategoriaService.save(subCategoria), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubCategoria> update(@PathVariable Long id, @RequestBody SubCategoria subCategoria) {
        return ResponseEntity.ok(subCategoriaService.update(id, subCategoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        subCategoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
