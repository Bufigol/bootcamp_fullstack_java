package com.bufigol.universidad.servicio;

import com.bufigol.universidad.excepciones.servicio.DuplicateResourceException;
import com.bufigol.universidad.interfaces.servicio.INT_RoleServicio;
import com.bufigol.universidad.modelo.Role;
import com.bufigol.universidad.repositorio.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RoleServicio implements INT_RoleServicio {

    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findByName(String name) {
        Assert.hasText(name, "El nombre del rol no puede estar vacío");
        log.debug("Buscando rol por nombre: {}", name);

        try {
            return roleRepository.findByName(name);
        } catch (Exception e) {
            log.error("Error al buscar rol por nombre {}: {}", name, e.getMessage());
            throw new RuntimeException("Error al buscar rol por nombre", e);
        }
    }

    @Override
    @Transactional
    public Role save(Role role) {
        Assert.notNull(role, "El rol no puede ser nulo");
        Assert.hasText(role.getAuthority(), "El nombre del rol no puede estar vacío");
        log.debug("Guardando rol: {}", role.getAuthority());

        try {
            // Verificar si ya existe un rol con el mismo nombre
            Optional<Role> existingRole = findByName(role.getAuthority());
            if (existingRole.isPresent() && !existingRole.get().getId().equals(role.getId())) {
                throw new DuplicateResourceException("Role", "name", role.getAuthority());
            }

            Role savedRole = roleRepository.save(role);
            log.info("Rol guardado exitosamente: {}", savedRole.getAuthority());
            return savedRole;

        } catch (DuplicateResourceException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al guardar rol {}: {}", role.getAuthority(), e.getMessage());
            throw new RuntimeException("Error al guardar el rol", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Set<Role>> validateAndGetRoles(Set<String> roleNames) {
        try {
            Set<Role> validatedRoles = new HashSet<>();
            for (String roleName : roleNames) {
                log.debug("Validando rol: {}", roleName);
                Optional<Role> roleOpt = findByName(roleName);
                if (roleOpt.isEmpty()) {
                    log.warn("Rol {} no encontrado en la base de datos", roleName);
                    return Optional.empty();
                }
                validatedRoles.add(roleOpt.get());
            }
            return Optional.of(validatedRoles);
        } catch (Exception e) {
            log.error("Error validando roles:", e);
            return Optional.empty();
        }
    }
}