package com.api.thrill.service;

import com.api.thrill.entity.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);
    Usuario actualizar(Long id, Usuario usuario);
    Optional<Usuario> buscarPorId(Long id);
    Optional<Usuario> buscarPorEmail(String email);
    List<Usuario> listarTodos();
    boolean existePorEmail(String email);
}