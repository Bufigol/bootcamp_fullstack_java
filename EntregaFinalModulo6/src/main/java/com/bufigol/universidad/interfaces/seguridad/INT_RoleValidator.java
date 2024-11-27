package com.bufigol.universidad.interfaces.seguridad;

import com.bufigol.universidad.modelo.Role;

import java.util.Set;

public interface INT_RoleValidator {
    Set<Role> validateAndNormalizeRoles(Set<String> roles);
    boolean isValidRole(String role);
    String normalizeRoleName(String role);
}
