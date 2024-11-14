package com.bufigol.universidad.dtos.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoResponseDTO {
    private Long id;
    private String rut;
    private String nombre;
    private String direccion;
    private Set<MateriaResponseDTO> materias;
    private LocalDateTime createdAt;
}