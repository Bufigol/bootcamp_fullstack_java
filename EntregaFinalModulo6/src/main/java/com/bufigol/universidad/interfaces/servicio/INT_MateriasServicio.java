package com.bufigol.universidad.interfaces.servicio;

import com.bufigol.universidad.dtos.modelo.MateriaRequestDTO;
import com.bufigol.universidad.dtos.modelo.MateriaResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface INT_MateriasServicio {
    MateriaResponseDTO create(MateriaRequestDTO materiaDTO);
    MateriaResponseDTO update(Long id, MateriaRequestDTO materiaDTO);
    void delete(Long id);
    Optional<MateriaResponseDTO> findById(Long id);
    List<MateriaResponseDTO> findByAlumnoId(Long alumnoId);
    Page<MateriaResponseDTO> findAll(Pageable pageable);
    boolean existsByNombreAndAlumnoId(String nombre, Long alumnoId);
}
