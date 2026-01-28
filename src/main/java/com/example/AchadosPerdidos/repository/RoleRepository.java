package com.example.AchadosPerdidos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AchadosPerdidos.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNome(String nome);
    boolean existsByNome(String nome);
}
