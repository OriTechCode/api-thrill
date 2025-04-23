package com.api.thrill.repository;

import com.api.thrill.entity.Talle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TalleRepository extends JpaRepository<Talle, Long> {
}
