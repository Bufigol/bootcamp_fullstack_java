package com.bufigol.universidad.interfaces.servicio;

import com.bufigol.universidad.dtos.modelo.AlumnoRequestDTO;
import com.bufigol.universidad.dtos.modelo.AlumnoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface INT_AlumnoServicio {
    AlumnoResponseDTO create(AlumnoRequestDTO alumnoDTO);
    AlumnoResponseDTO update(Long id, AlumnoRequestDTO alumnoDTO);
    void delete(Long id);
    Optional<AlumnoResponseDTO> findById(Long id);
    Optional<AlumnoResponseDTO> findByRut(String rut);
    List<AlumnoResponseDTO> findByNombreContaining(String nombre);
    Page<AlumnoResponseDTO> findAll(Pageable pageable);
    boolean existsByRut(String rut);
}
