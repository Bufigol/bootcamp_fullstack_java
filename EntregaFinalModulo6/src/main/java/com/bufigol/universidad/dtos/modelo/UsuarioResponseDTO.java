package com.bufigol.universidad.dtos.modelo;

import com.bufigol.universidad.modelo.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioResponseDTO {

    private Long id;

    private String name;

    private String username;

    private String email;

    private Set<String> roles;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;


    public static Set<String> convertRolesToStrings(Set<Role> roles) {
        return roles.stream()
                .map(Role::getAuthority)
                .collect(Collectors.toSet());
    }

    public boolean hasRole(String roleName) {
        return roles != null && roles.contains(roleName);
    }

    public boolean isAdmin() {
        return hasRole("ROLE_ADMIN");
    }

    public long getDaysSinceCreation() {
        if (createdAt == null) return 0;
        return java.time.Duration.between(createdAt, LocalDateTime.now()).toDays();
    }
}