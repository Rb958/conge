package com.conge.conge.repository;

import com.conge.conge.entity.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeRepository extends JpaRepository<Employe, Long> {
    Optional<Employe> findByNomUtilisateur(String username);
}
