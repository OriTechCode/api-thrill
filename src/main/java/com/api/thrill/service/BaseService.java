package com.api.thrill.service;

import java.util.List;
import java.util.Optional;

public interface BaseService <T, ID> {
    List<T> findAll(); // Este ahora traerá solo entidades no eliminadas
    Optional<T> findById(ID id);
    T save(T entity);
    T update(ID id, T entity);
    void delete(ID id);
    List<T> findAllDeleted(); // Nuevo método para obtener entidades eliminadas lógicamente
}