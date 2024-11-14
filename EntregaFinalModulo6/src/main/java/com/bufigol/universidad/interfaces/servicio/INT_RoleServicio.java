package com.bufigol.universidad.interfaces.servicio;

import com.bufigol.universidad.modelo.Role;

import java.util.Optional;
import java.util.Set;

public interface INT_RoleServicio {
    Optional<Role> findByName(String name);
    Role save(Role role);
    Optional<Set<Role>> validateAndGetRoles(Set<String> roleNames);
}
