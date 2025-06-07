package com.api.thrill.controller;

import com.api.thrill.entity.Descuento;
import com.api.thrill.service.DescuentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/descuentos")
public class DescuentoController extends BaseController<Descuento, Long> {

    private final DescuentoService descuentoService;

    public DescuentoController(DescuentoService descuentoService) {
        super(descuentoService);
        this.descuentoService = descuentoService;
    }

    @GetMapping("/vigentes")
    public ResponseEntity<List<Descuento>> findDescuentosVigentes() {

        return ResponseEntity.ok(descuentoService.findDescuentosVigentes());
    }
}