package com.api.thrill.service;

import com.api.thrill.entity.Usuario;

public interface UsuarioService extends BaseService<Usuario, Long> {

    boolean existePorEmail(String email); // este todavia no estamos seguros de usar o no
}