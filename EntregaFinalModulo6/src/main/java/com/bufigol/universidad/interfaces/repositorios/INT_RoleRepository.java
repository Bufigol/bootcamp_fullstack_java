package com.bufigol.universidad.interfaces.repositorios;

import com.bufigol.universidad.modelo.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface INT_RoleRepository extends JpaRepository<Role, Long> {
    // Buscar por nombre del rol
    Optional<Role> findByName(String name);
}
