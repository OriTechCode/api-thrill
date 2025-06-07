package com.api.thrill.controller;

import com.api.thrill.entity.Tipo;
import com.api.thrill.service.TipoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tipos")
public class TipoController extends BaseController<Tipo, Long> {

    public TipoController(TipoService tipoService) {
        super(tipoService);
    }
}