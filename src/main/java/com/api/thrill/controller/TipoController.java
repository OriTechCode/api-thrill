package com.api.thrill.controller;

import com.api.thrill.entity.Tipo;
import com.api.thrill.service.TipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tipos")
public class TipoController {

    @Autowired
    private TipoService tipoService;

    @GetMapping("")
    public ResponseEntity<List<Tipo>> findAll() {
        return ResponseEntity.ok(tipoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tipo> findById(@PathVariable Long id) {
        return ResponseEntity.ok(tipoService.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<Tipo> save(@RequestBody Tipo tipo) {
        return new ResponseEntity<>(tipoService.save(tipo), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tipo> update(@PathVariable Long id, @RequestBody Tipo tipo) {
        return ResponseEntity.ok(tipoService.update(id, tipo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tipoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}