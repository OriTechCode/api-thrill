package com.api.thrill.service;

import com.api.thrill.entity.Producto;
import com.api.thrill.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductoService {
    List<Producto> findAll();
    Producto findById(Long id);
    Producto save(Producto producto);
    Producto update(Long id, Producto producto);
    void deleteLogic(Long id);
    List<Producto> findByNombre(String nombre);
    List<Producto> findByMarca(String marca);
}