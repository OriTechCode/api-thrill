package com.api.thrill.repository;

import com.api.thrill.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u WHERE u.eliminado = false AND u.username = :username")
    Optional<Usuario> findByUsername(String username);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE u.eliminado = false AND u.email = :email")
    Optional<Usuario> findByEmail(String email);
}