package com.bufigol.universidad.dtos.mappers;

import com.bufigol.universidad.dtos.modelo.UsuarioResponseDTO;
import com.bufigol.universidad.modelo.Role;
import com.bufigol.universidad.modelo.Usuario;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {

    public UsuarioResponseDTO toDto(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .name(usuario.getName())
                .username(usuario.getUsername())
                .email(usuario.getEmail())
                .roles(mapRoles(usuario.getRoles()))
                .createdAt(usuario.getCreatedAt())
                .build();
    }

    private Set<String> mapRoles(Set<Role> roles) {
        if (roles == null) {
            return Set.of();
        }
        return roles.stream()
                .map(Role::getAuthority)
                .collect(Collectors.toSet());
    }
}