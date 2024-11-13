package com.bufigol.universidad.interfaces.servicio;

import com.bufigol.universidad.modelo.Role;

import java.util.Optional;

public interface INT_RoleServicio {
    Optional<Role> findByName(String name);
    Role save(Role role);
}
