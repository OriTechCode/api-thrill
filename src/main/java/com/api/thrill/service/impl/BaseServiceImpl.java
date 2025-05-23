package com.api.thrill.service.impl;

import com.api.thrill.entity.Base;
import com.api.thrill.service.BaseService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class BaseServiceImpl<T extends Base, ID> implements BaseService<T, ID> {

    protected final JpaRepository<T, ID> repository; // Sin cambios

    protected BaseServiceImpl(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public List<T> findAll() {

        return repository.findAll().stream()
                .filter(entity -> !entity.getEliminado())
                .toList();
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public T save(T entity) {
        entity.setEliminado(false);
        return repository.save(entity);
    }

    @Override
    public T update(ID id, T entity) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Entidad no encontrada con ID: " + id);
        }
        return repository.save(entity);
    }

    @Override
    public void delete(ID id) {
        Optional<T> entityOptional = repository.findById(id);
        if (entityOptional.isPresent()) {
            T entity = entityOptional.get();
            entity.setEliminado(true);
            repository.save(entity);
        } else {
            throw new RuntimeException("Entidad no encontrada con ID: " + id);
        }
    }
}