package com.bufigol.universidad.interfaces.controladores;


import com.bufigol.universidad.dtos.modelo.AlumnoRequestDTO;
import com.bufigol.universidad.dtos.modelo.AlumnoResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface INT_AlumnoController {
    @PostMapping
    ResponseEntity<AlumnoResponseDTO> createAlumno(@Valid @RequestBody AlumnoRequestDTO alumnoRequest);

    @PutMapping("/{id}")
    ResponseEntity<AlumnoResponseDTO> updateAlumno(@PathVariable Long id, @Valid @RequestBody AlumnoRequestDTO alumnoRequest);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteAlumno(@PathVariable Long id);

    @GetMapping("/{id}")
    ResponseEntity<AlumnoResponseDTO> getAlumnoById(@PathVariable Long id);

    @GetMapping("/rut/{rut}")
    ResponseEntity<AlumnoResponseDTO> getAlumnoByRut(@PathVariable String rut);

    @GetMapping("/search")
    ResponseEntity<List<AlumnoResponseDTO>> searchAlumnosByNombre(@RequestParam String nombre);

    @GetMapping
    ResponseEntity<Page<AlumnoResponseDTO>> getAllAlumnos(Pageable pageable);
}
