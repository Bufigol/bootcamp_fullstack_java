package com.bufigol.universidad.controlador.rest;

import com.bufigol.universidad.dtos.modelo.MateriaRequestDTO;
import com.bufigol.universidad.dtos.modelo.MateriaResponseDTO;
import com.bufigol.universidad.interfaces.controladores.rest.INT_MateriaController;
import com.bufigol.universidad.interfaces.servicio.INT_MateriasServicio;
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
@RequestMapping("/api/materias")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")  // Solo administradores pueden acceder a estos endpoints
public class MateriaController implements INT_MateriaController {

    private final INT_MateriasServicio materiasServicio;

    @Override
    @PostMapping
    public ResponseEntity<MateriaResponseDTO> createMateria(@Valid @RequestBody MateriaRequestDTO materiaRequest) {
        log.debug("Solicitud de creación de materia recibida: {}", materiaRequest);
        try {
            MateriaResponseDTO createdMateria = materiasServicio.create(materiaRequest);
            log.info("Materia creada exitosamente con ID: {}", createdMateria.getId());
            return ResponseEntity.ok(createdMateria);
        } catch (Exception e) {
            log.error("Error al crear materia: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<MateriaResponseDTO> updateMateria(
            @PathVariable Long id,
            @Valid @RequestBody MateriaRequestDTO materiaRequest) {
        log.debug("Solicitud de actualización para materia ID {}: {}", id, materiaRequest);
        try {
            MateriaResponseDTO updatedMateria = materiasServicio.update(id, materiaRequest);
            log.info("Materia actualizada exitosamente con ID: {}", id);
            return ResponseEntity.ok(updatedMateria);
        } catch (Exception e) {
            log.error("Error al actualizar materia con ID {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMateria(@PathVariable Long id) {
        log.debug("Solicitud de eliminación para materia ID: {}", id);
        try {
            materiasServicio.delete(id);
            log.info("Materia eliminada exitosamente con ID: {}", id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error al eliminar materia con ID {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")  // Permitir acceso a usuarios normales también
    public ResponseEntity<MateriaResponseDTO> getMateriaById(@PathVariable Long id) {
        log.debug("Buscando materia por ID: {}", id);
        try {
            return materiasServicio.findById(id)
                    .map(materia -> {
                        log.info("Materia encontrada con ID: {}", id);
                        return ResponseEntity.ok(materia);
                    })
                    .orElseGet(() -> {
                        log.warn("No se encontró materia con ID: {}", id);
                        return ResponseEntity.notFound().build();
                    });
        } catch (Exception e) {
            log.error("Error al buscar materia por ID {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    @GetMapping("/alumno/{alumnoId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<List<MateriaResponseDTO>> getMateriasByAlumnoId(@PathVariable Long alumnoId) {
        log.debug("Buscando materias para alumno ID: {}", alumnoId);
        try {
            List<MateriaResponseDTO> materias = materiasServicio.findByAlumnoId(alumnoId);
            if (materias.isEmpty()) {
                log.info("No se encontraron materias para alumno ID: {}", alumnoId);
                return ResponseEntity.noContent().build();
            }
            log.info("Se encontraron {} materias para alumno ID: {}", materias.size(), alumnoId);
            return ResponseEntity.ok(materias);
        } catch (Exception e) {
            log.error("Error al buscar materias por alumno ID {}: {}", alumnoId, e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Page<MateriaResponseDTO>> getAllMaterias(Pageable pageable) {
        log.debug("Solicitando página {} de materias con tamaño {}",
                pageable.getPageNumber(), pageable.getPageSize());
        try {
            Page<MateriaResponseDTO> materiasPage = materiasServicio.findAll(pageable);
            if (materiasPage.isEmpty()) {
                log.info("No se encontraron materias en la página solicitada");
                return ResponseEntity.noContent().build();
            }
            log.info("Se encontraron {} materias en la página {}",
                    materiasPage.getNumberOfElements(), materiasPage.getNumber());
            return ResponseEntity.ok(materiasPage);
        } catch (Exception e) {
            log.error("Error al obtener la página de materias: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}