package com.bufigol.universidad.interfaces.controladores.rest;

import com.bufigol.universidad.dtos.modelo.UsuarioResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

public interface INT_AdminController {
    @GetMapping("/stats/alumnos")
    ResponseEntity<Map<String, Object>> getAlumnosStats();

    @GetMapping("/stats/materias")
    ResponseEntity<Map<String, Object>> getMateriasStats();

    @GetMapping("/users")
    ResponseEntity<Page<UsuarioResponseDTO>> getAllUsers(Pageable pageable);

    @PostMapping("/users/{userId}/roles")
    ResponseEntity<UsuarioResponseDTO> updateUserRoles(@PathVariable Long userId, @RequestBody Set<String> roles);

}
