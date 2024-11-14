package com.bufigol.universidad.interfaces.controladores;

import com.bufigol.universidad.dtos.modelo.MateriaRequestDTO;
import com.bufigol.universidad.dtos.modelo.MateriaResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
public interface INT_MateriaController {
    @PostMapping
    ResponseEntity<MateriaResponseDTO> createMateria(@Valid @RequestBody MateriaRequestDTO materiaRequest);

    @PutMapping("/{id}")
    ResponseEntity<MateriaResponseDTO> updateMateria(@PathVariable Long id, @Valid @RequestBody MateriaRequestDTO materiaRequest);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteMateria(@PathVariable Long id);

    @GetMapping("/{id}")
    ResponseEntity<MateriaResponseDTO> getMateriaById(@PathVariable Long id);

    @GetMapping("/alumno/{alumnoId}")
    ResponseEntity<List<MateriaResponseDTO>> getMateriasByAlumnoId(@PathVariable Long alumnoId);

    @GetMapping
    ResponseEntity<Page<MateriaResponseDTO>> getAllMaterias(Pageable pageable);
}
