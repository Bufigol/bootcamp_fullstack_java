package com.bufigol.universidad.seguridad.servicios;

import com.bufigol.universidad.excepciones.servicio.ResourceNotFoundException;
import com.bufigol.universidad.interfaces.seguridad.INT_RoleValidator;
import com.bufigol.universidad.interfaces.servicio.INT_RoleServicio;
import com.bufigol.universidad.modelo.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import static com.bufigol.universidad.seguridad.constantes.ConstantesSeguridad.ROLE_PREFIX;

@Slf4j
@Service
@RequiredArgsConstructor

public class RoleValidator implements INT_RoleValidator {

    private final INT_RoleServicio roleServicio;


    @Override
    public Set<Role> validateAndNormalizeRoles(Set<String> roles) {
        log.debug("Validando y normalizando roles: {}", roles);
        Set<Role> validatedRoles = new HashSet<>();

        Set<String> normalizedRoleNames = roles.stream()
                .map(this::normalizeRoleName)
                .collect(Collectors.toSet());

        for (String roleName : normalizedRoleNames) {
            Role role = roleServicio.findByName(roleName)
                    .orElseThrow(() -> new ResourceNotFoundException("Role", "name", roleName));
            validatedRoles.add(role);
        }

        return validatedRoles;
    }

    @Override
    public boolean isValidRole(String role) {
        String normalizedRole = normalizeRoleName(role);
        return roleServicio.findByName(normalizedRole).isPresent();
    }

    @Override
    public String normalizeRoleName(String role) {
        return role.startsWith(ROLE_PREFIX) ? role : ROLE_PREFIX + role;
    }
}
