package com.api.thrill.repository;

import com.api.thrill.entity.SubCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoriaRepository extends JpaRepository<SubCategoria, Long> {
    List<SubCategoria> findByCategoriaId(Long categoriaId);
}