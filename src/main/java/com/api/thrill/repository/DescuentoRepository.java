package com.api.thrill.repository;

import com.api.thrill.entity.Descuento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DescuentoRepository extends JpaRepository<Descuento, Long> {
    List<Descuento> findByFechaFinGreaterThanEqualAndFechaInicioLessThanEqual(LocalDate fecha, LocalDate fecha2);
}
