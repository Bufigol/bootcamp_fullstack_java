package com.bufigol.universidad.servicio;

import com.bufigol.universidad.dtos.mappers.MateriaMapper;
import com.bufigol.universidad.dtos.modelo.MateriaRequestDTO;
import com.bufigol.universidad.dtos.modelo.MateriaResponseDTO;
import com.bufigol.universidad.excepciones.DuplicateResourceException;
import com.bufigol.universidad.excepciones.ResourceNotFoundException;
import com.bufigol.universidad.interfaces.servicio.INT_MateriasServicio;
import com.bufigol.universidad.repositorio.MateriaRepository;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MateriasServicio implements INT_MateriasServicio {

    private final MateriaRepository materiaRepository;
    private final MateriaMapper materiaMapper;

    @Override
    @Transactional
    public MateriaResponseDTO create(MateriaRequestDTO materiaDTO) {
        Assert.notNull(materiaDTO, "El DTO de la materia no puede ser nulo");
        Assert.hasText(materiaDTO.getNombre(), "El nombre de la materia no puede estar vacío");
        Assert.notNull(materiaDTO.getAlumnoId(), "El ID del alumno no puede ser nulo");

        log.debug("Creando nueva materia con nombre: {} para alumno ID: {}",
                materiaDTO.getNombre(), materiaDTO.getAlumnoId());

        if (existsByNombreAndAlumnoId(materiaDTO.getNombre(), materiaDTO.getAlumnoId())) {
            throw new DuplicateResourceException("Materia", "nombre y alumnoId",
                    materiaDTO.getNombre() + " - " + materiaDTO.getAlumnoId());
        }

        try {
            var materia = materiaMapper.toEntity(materiaDTO);
            var savedMateria = materiaRepository.save(materia);
            log.info("Materia creada exitosamente con ID: {}", savedMateria.getId());
            return materiaMapper.toDto(savedMateria);
        } catch (Exception e) {
            log.error("Error al crear materia: {}", e.getMessage());
            throw new RuntimeException("Error al crear la materia", e);
        }
    }

    @Override
    @Transactional
    public MateriaResponseDTO update(Long id, MateriaRequestDTO materiaDTO) {
        Assert.notNull(id, "El ID no puede ser nulo");
        Assert.notNull(materiaDTO, "El DTO de la materia no puede ser nulo");
        log.debug("Actualizando materia con ID: {}", id);

        var existingMateria = materiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Materia", "id", id));

        // Verificar si el nuevo nombre ya existe para el mismo alumno (si se está cambiando)
        if (!existingMateria.getNombre().equals(materiaDTO.getNombre()) &&
                existsByNombreAndAlumnoId(materiaDTO.getNombre(), materiaDTO.getAlumnoId())) {
            throw new DuplicateResourceException("Materia", "nombre y alumnoId",
                    materiaDTO.getNombre() + " - " + materiaDTO.getAlumnoId());
        }

        try {
            materiaMapper.updateEntityFromDto(materiaDTO, existingMateria);
            var updatedMateria = materiaRepository.save(existingMateria);
            log.info("Materia actualizada exitosamente con ID: {}", id);
            return materiaMapper.toDto(updatedMateria);
        } catch (Exception e) {
            log.error("Error al actualizar materia con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al actualizar la materia", e);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Assert.notNull(id, "El ID no puede ser nulo");
        log.debug("Eliminando materia con ID: {}", id);

        if (!materiaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Materia", "id", id);
        }

        try {
            materiaRepository.deleteById(id);
            log.info("Materia eliminada exitosamente con ID: {}", id);
        } catch (Exception e) {
            log.error("Error al eliminar materia con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al eliminar la materia", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MateriaResponseDTO> findById(Long id) {
        Assert.notNull(id, "El ID no puede ser nulo");
        log.debug("Buscando materia por ID: {}", id);

        try {
            return materiaRepository.findById(id)
                    .map(materiaMapper::toDto);
        } catch (Exception e) {
            log.error("Error al buscar materia por ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al buscar la materia", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<MateriaResponseDTO> findByAlumnoId(Long alumnoId) {
        Assert.notNull(alumnoId, "El ID del alumno no puede ser nulo");
        log.debug("Buscando materias para el alumno con ID: {}", alumnoId);

        try {
            return materiaRepository.findByAlumnoId(alumnoId)
                    .stream()
                    .map(materiaMapper::toDto)
                    .toList();
        } catch (Exception e) {
            log.error("Error al buscar materias por alumnoId {}: {}", alumnoId, e.getMessage());
            throw new RuntimeException("Error al buscar materias por alumno", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MateriaResponseDTO> findAll(Pageable pageable) {
        Assert.notNull(pageable, "El objeto Pageable no puede ser nulo");
        log.debug("Obteniendo página {} de materias", pageable.getPageNumber());

        try {
            return materiaRepository.findAll(pageable)
                    .map(materiaMapper::toDto);
        } catch (Exception e) {
            log.error("Error al obtener la página de materias: {}", e.getMessage());
            throw new RuntimeException("Error al obtener la página de materias", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByNombreAndAlumnoId(String nombre, Long alumnoId) {
        Assert.hasText(nombre, "El nombre no puede estar vacío");
        Assert.notNull(alumnoId, "El ID del alumno no puede ser nulo");
        log.debug("Verificando existencia de materia '{}' para alumno ID: {}", nombre, alumnoId);

        try {
            return materiaRepository.existsByNombreAndAlumnoId(nombre, alumnoId);
        } catch (Exception e) {
            log.error("Error al verificar existencia de materia: {}", e.getMessage());
            throw new RuntimeException("Error al verificar existencia de materia", e);
        }
    }
}
