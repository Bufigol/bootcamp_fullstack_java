package com.bufigol.universidad.controlador;

import com.bufigol.universidad.dtos.mappers.UsuarioMapper;
import com.bufigol.universidad.dtos.modelo.AlumnoResponseDTO;
import com.bufigol.universidad.dtos.modelo.UsuarioResponseDTO;
import com.bufigol.universidad.interfaces.controladores.INT_AdminController;
import com.bufigol.universidad.interfaces.servicio.INT_AlumnoServicio;
import com.bufigol.universidad.interfaces.servicio.INT_MateriasServicio;
import com.bufigol.universidad.interfaces.servicio.INT_RoleServicio;
import com.bufigol.universidad.interfaces.servicio.INT_UsuarioServicio;
import com.bufigol.universidad.modelo.Role;
import com.bufigol.universidad.modelo.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController implements INT_AdminController {

    private final INT_AlumnoServicio alumnoServicio;
    private final INT_MateriasServicio materiasServicio;
    private final INT_RoleServicio roleServicio;
    private final INT_UsuarioServicio usuarioServicio;
    private final UsuarioMapper usuarioMapper;

    @Override
    @GetMapping("/stats/alumnos")
    public ResponseEntity<Map<String, Object>> getAlumnosStats() {
        log.debug("Obteniendo estadísticas de alumnos");
        try {
            Map<String, Object> stats = new HashMap<>();

            // Obtener estadísticas usando los métodos disponibles
            Page<AlumnoResponseDTO> alumnosPage = alumnoServicio.findAll(Pageable.unpaged());
            stats.put("totalAlumnos", alumnosPage.getTotalElements());

            // Calcular promedio de materias por alumno
            double promedioMaterias = alumnosPage.getContent().stream()
                    .filter(alumno -> alumno.getMaterias() != null)
                    .mapToDouble(alumno -> alumno.getMaterias().size())
                    .average()
                    .orElse(0.0);

            stats.put("promedioMateriasXAlumno", promedioMaterias);

            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            log.error("Error al obtener estadísticas de alumnos: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    @GetMapping("/stats/materias")
    public ResponseEntity<Map<String, Object>> getMateriasStats() {
        log.debug("Obteniendo estadísticas de materias");
        try {
            Map<String, Object> stats = new HashMap<>();

            // Obtener total de materias
            long totalMaterias = materiasServicio.findAll(Pageable.unpaged()).getTotalElements();
            stats.put("totalMaterias", totalMaterias);

            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            log.error("Error al obtener estadísticas de materias: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    @GetMapping("/users")
    public ResponseEntity<Page<UsuarioResponseDTO>> getAllUsers(Pageable pageable) {
        log.debug("Solicitando lista de usuarios. Página: {}, Tamaño: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            List<UsuarioResponseDTO> userDTOs = new ArrayList<>();

            // Obtener usuarios conocidos
            Optional<Usuario> adminUser = usuarioServicio.findByUsername("admin");
            adminUser.ifPresent(usuario -> userDTOs.add(usuarioMapper.toDto(usuario)));

            // Aplicar paginación
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), userDTOs.size());
            List<UsuarioResponseDTO> paginatedUsers = userDTOs.subList(start, end);

            Page<UsuarioResponseDTO> userPage = new PageImpl<>(
                    paginatedUsers,
                    pageable,
                    userDTOs.size()
            );

            log.info("Se encontraron {} usuarios en total", userDTOs.size());
            return ResponseEntity.ok(userPage);

        } catch (Exception e) {
            log.error("Error al obtener la lista de usuarios: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    @PostMapping("/users/{userId}/roles")
    public ResponseEntity<UsuarioResponseDTO> updateUserRoles(
            @PathVariable Long userId,
            @RequestBody Set<String> roles) {
        log.debug("Actualizando roles para usuario ID: {}. Nuevos roles: {}", userId, roles);

        try {
            // Validar roles
            Optional<Set<Role>> validatedRolesOpt = roleServicio.validateAndGetRoles(roles);
            if (validatedRolesOpt.isEmpty()) {
                log.warn("Roles inválidos proporcionados: {}", roles);
                return ResponseEntity.badRequest().build();
            }

            // Buscar usuario
            Optional<Usuario> usuarioOpt = usuarioServicio.findByUsername("admin");
            if (usuarioOpt.isEmpty()) {
                log.warn("Usuario no encontrado con ID: {}", userId);
                return ResponseEntity.notFound().build();
            }

            Usuario usuario = usuarioOpt.get();
            if (!usuario.getId().equals(userId)) {
                log.warn("ID de usuario no coincide con el solicitado");
                return ResponseEntity.notFound().build();
            }

            // Actualizar roles
            usuario.setRoles(validatedRolesOpt.get());
            UsuarioResponseDTO updatedUser = usuarioMapper.toDto(usuario);

            log.info("Roles actualizados exitosamente para usuario ID: {}", userId);
            return ResponseEntity.ok(updatedUser);

        } catch (Exception e) {
            log.error("Error al actualizar roles del usuario: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}