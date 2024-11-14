package com.bufigol.universidad.servicio;

import com.bufigol.universidad.dtos.mappers.AlumnoMapper;
import com.bufigol.universidad.dtos.modelo.AlumnoRequestDTO;
import com.bufigol.universidad.dtos.modelo.AlumnoResponseDTO;
import com.bufigol.universidad.excepciones.servicio.DuplicateResourceException;
import com.bufigol.universidad.excepciones.servicio.ResourceNotFoundException;
import com.bufigol.universidad.interfaces.servicio.INT_AlumnoServicio;
import com.bufigol.universidad.modelo.Alumno;
import com.bufigol.universidad.repositorio.AlumnoRepository;
import com.bufigol.universidad.utils.Comprobadores;
import com.bufigol.universidad.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AlumnoServicio implements INT_AlumnoServicio {

    private final AlumnoRepository alumnoRepository;
    private final AlumnoMapper alumnoMapper;

    @Override
    @Transactional
    public AlumnoResponseDTO create(AlumnoRequestDTO alumnoDTO) {
        Assert.notNull(alumnoDTO, "El DTO del alumno no puede ser nulo");
        log.debug("Creando nuevo alumno con RUT: {}", alumnoDTO.getRut());

        if (!Comprobadores.isValidRUT(alumnoDTO.getRut())) {
            throw new IllegalArgumentException("El RUT proporcionado no es válido");
        }

        if (alumnoRepository.existsByRut(alumnoDTO.getRut())) {
            throw new DuplicateResourceException("Alumno", "rut", alumnoDTO.getRut());
        }

        try {
            Alumno alumno = alumnoMapper.toEntity(alumnoDTO);
            alumno.setPassword(PasswordUtils.generarPasswordSegura(alumno.getPassword()));

            Alumno savedAlumno = alumnoRepository.save(alumno);
            log.info("Alumno creado exitosamente con ID: {}", savedAlumno.getId());

            return alumnoMapper.toDto(savedAlumno);
        } catch (Exception e) {
            log.error("Error al crear alumno: {}", e.getMessage());
            throw new RuntimeException("Error al crear el alumno", e);
        }
    }

    @Override
    @Transactional
    public AlumnoResponseDTO update(Long id, AlumnoRequestDTO alumnoDTO) {
        Assert.notNull(id, "El ID no puede ser nulo");
        Assert.notNull(alumnoDTO, "El DTO del alumno no puede ser nulo");
        log.debug("Actualizando alumno con ID: {}", id);

        if (!Comprobadores.isValidRUT(alumnoDTO.getRut())) {
            throw new IllegalArgumentException("El RUT proporcionado no es válido");
        }

        Alumno existingAlumno = alumnoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alumno", "id", id));

        // Verificar si el nuevo RUT ya existe y no pertenece al mismo alumno
        if (!existingAlumno.getRut().equals(alumnoDTO.getRut()) &&
                alumnoRepository.existsByRut(alumnoDTO.getRut())) {
            throw new DuplicateResourceException("Alumno", "rut", alumnoDTO.getRut());
        }

        try {
            alumnoMapper.updateEntityFromDto(alumnoDTO, existingAlumno);
            existingAlumno.setPassword(PasswordUtils.generarPasswordSegura(alumnoDTO.getPassword()));

            Alumno updatedAlumno = alumnoRepository.save(existingAlumno);
            log.info("Alumno actualizado exitosamente con ID: {}", id);

            return alumnoMapper.toDto(updatedAlumno);
        } catch (Exception e) {
            log.error("Error al actualizar alumno con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al actualizar el alumno", e);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Assert.notNull(id, "El ID no puede ser nulo");
        log.debug("Eliminando alumno con ID: {}", id);

        if (!alumnoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Alumno", "id", id);
        }

        try {
            alumnoRepository.deleteById(id);
            log.info("Alumno eliminado exitosamente con ID: {}", id);
        } catch (Exception e) {
            log.error("Error al eliminar alumno con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al eliminar el alumno", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AlumnoResponseDTO> findById(Long id) {
        Assert.notNull(id, "El ID no puede ser nulo");
        log.debug("Buscando alumno por ID: {}", id);

        return alumnoRepository.findById(id)
                .map(alumnoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AlumnoResponseDTO> findByRut(String rut) {
        Assert.hasText(rut, "El RUT no puede estar vacío");
        log.debug("Buscando alumno por RUT: {}", rut);

        if (!Comprobadores.isValidRUT(rut)) {
            throw new IllegalArgumentException("El RUT proporcionado no es válido");
        }

        return alumnoRepository.findByRut(rut)
                .map(alumnoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlumnoResponseDTO> findByNombreContaining(String nombre) {
        Assert.hasText(nombre, "El nombre no puede estar vacío");
        log.debug("Buscando alumnos por nombre que contenga: {}", nombre);

        try {
            return alumnoRepository.findByNombreContainingIgnoreCase(nombre)
                    .stream()
                    .map(alumnoMapper::toDto)
                    .toList();
        } catch (Exception e) {
            log.error("Error al buscar alumnos por nombre: {}", e.getMessage());
            throw new RuntimeException("Error al buscar alumnos por nombre", e);
        }
    }


    @Override
    @Transactional(readOnly = true)
    public Page<AlumnoResponseDTO> findAll(Pageable pageable) {
        Assert.notNull(pageable, "El objeto Pageable no puede ser nulo");
        log.debug("Obteniendo página {} de alumnos", pageable.getPageNumber());

        try {
            return alumnoRepository.findAll(pageable)
                    .map(alumnoMapper::toDto);
        } catch (Exception e) {
            log.error("Error al obtener la página de alumnos: {}", e.getMessage());
            throw new RuntimeException("Error al obtener la página de alumnos", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByRut(String rut) {
        Assert.hasText(rut, "El RUT no puede estar vacío");
        log.debug("Verificando existencia de alumno por RUT: {}", rut);

        if (!Comprobadores.isValidRUT(rut)) {
            throw new IllegalArgumentException("El RUT proporcionado no es válido");
        }

        return alumnoRepository.existsByRut(rut);
    }
}