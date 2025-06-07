package com.api.thrill.controller;

import com.api.thrill.entity.Talle;
import com.api.thrill.service.TalleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/talles")
public class TalleController extends BaseController<Talle, Long> {

    private final TalleService talleService;

    public TalleController(TalleService talleService) {
        super(talleService);
        this.talleService = talleService;
    }

    @GetMapping("/tipo/{tipoId}")
    public ResponseEntity<List<Talle>> findByTipoId(@PathVariable Long tipoId) {

        return ResponseEntity.ok(talleService.findByTipoId(tipoId));
    }


}