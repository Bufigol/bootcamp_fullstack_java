package com.bufigol.universidad.controlador.rest;

import com.bufigol.universidad.dtos.modelo.AlumnoRequestDTO;
import com.bufigol.universidad.dtos.modelo.AlumnoResponseDTO;
import com.bufigol.universidad.interfaces.controladores.rest.INT_AlumnoController;
import com.bufigol.universidad.interfaces.servicio.INT_AlumnoServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/alumnos")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")  // Solo administradores pueden acceder a estos endpoints
public class AlumnoController implements INT_AlumnoController {

    private final INT_AlumnoServicio alumnoServicio;

    @Override
    @PostMapping
    public ResponseEntity<AlumnoResponseDTO> createAlumno(@Valid @RequestBody AlumnoRequestDTO alumnoRequest) {
        log.debug("Solicitud de creación de alumno recibida: {}", alumnoRequest);
        try {
            AlumnoResponseDTO createdAlumno = alumnoServicio.create(alumnoRequest);
            log.info("Alumno creado exitosamente con ID: {}", createdAlumno.getId());
            return ResponseEntity.ok(createdAlumno);
        } catch (Exception e) {
            log.error("Error al crear alumno: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<AlumnoResponseDTO> updateAlumno(
            @PathVariable Long id,
            @Valid @RequestBody AlumnoRequestDTO alumnoRequest) {
        log.debug("Solicitud de actualización para alumno ID {}: {}", id, alumnoRequest);
        try {
            AlumnoResponseDTO updatedAlumno = alumnoServicio.update(id, alumnoRequest);
            log.info("Alumno actualizado exitosamente con ID: {}", id);
            return ResponseEntity.ok(updatedAlumno);
        } catch (Exception e) {
            log.error("Error al actualizar alumno con ID {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlumno(@PathVariable Long id) {
        log.debug("Solicitud de eliminación para alumno ID: {}", id);
        try {
            alumnoServicio.delete(id);
            log.info("Alumno eliminado exitosamente con ID: {}", id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error al eliminar alumno con ID {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")  // Permitir acceso a usuarios normales también
    public ResponseEntity<AlumnoResponseDTO> getAlumnoById(@PathVariable Long id) {
        log.debug("Buscando alumno por ID: {}", id);
        try {
            return alumnoServicio.findById(id)
                    .map(alumno -> {
                        log.info("Alumno encontrado con ID: {}", id);
                        return ResponseEntity.ok(alumno);
                    })
                    .orElseGet(() -> {
                        log.warn("No se encontró alumno con ID: {}", id);
                        return ResponseEntity.notFound().build();
                    });
        } catch (Exception e) {
            log.error("Error al buscar alumno por ID {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    @GetMapping("/rut/{rut}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<AlumnoResponseDTO> getAlumnoByRut(@PathVariable String rut) {
        log.debug("Buscando alumno por RUT: {}", rut);
        try {
            return alumnoServicio.findByRut(rut)
                    .map(alumno -> {
                        log.info("Alumno encontrado con RUT: {}", rut);
                        return ResponseEntity.ok(alumno);
                    })
                    .orElseGet(() -> {
                        log.warn("No se encontró alumno con RUT: {}", rut);
                        return ResponseEntity.notFound().build();
                    });
        } catch (Exception e) {
            log.error("Error al buscar alumno por RUT {}: {}", rut, e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<List<AlumnoResponseDTO>> searchAlumnosByNombre(
            @RequestParam String nombre) {
        log.debug("Buscando alumnos por nombre que contenga: {}", nombre);
        try {
            List<AlumnoResponseDTO> alumnos = alumnoServicio.findByNombreContaining(nombre);
            if (alumnos.isEmpty()) {
                log.info("No se encontraron alumnos con nombre que contenga: {}", nombre);
                return ResponseEntity.noContent().build();
            }
            log.info("Se encontraron {} alumnos con nombre que contiene: {}", alumnos.size(), nombre);
            return ResponseEntity.ok(alumnos);
        } catch (Exception e) {
            log.error("Error al buscar alumnos por nombre {}: {}", nombre, e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Page<AlumnoResponseDTO>> getAllAlumnos(Pageable pageable) {
        log.debug("Solicitando página {} de alumnos con tamaño {}",
                pageable.getPageNumber(), pageable.getPageSize());
        try {
            Page<AlumnoResponseDTO> alumnosPage = alumnoServicio.findAll(pageable);
            if (alumnosPage.isEmpty()) {
                log.info("No se encontraron alumnos en la página solicitada");
                return ResponseEntity.noContent().build();
            }
            log.info("Se encontraron {} alumnos en la página {}",
                    alumnosPage.getNumberOfElements(), alumnosPage.getNumber());
            return ResponseEntity.ok(alumnosPage);
        } catch (Exception e) {
            log.error("Error al obtener la página de alumnos: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}