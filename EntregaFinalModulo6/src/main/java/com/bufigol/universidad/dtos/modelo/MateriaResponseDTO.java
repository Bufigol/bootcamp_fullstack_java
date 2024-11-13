package com.bufigol.universidad.dtos.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MateriaResponseDTO {
    private Long id;
    private String nombre;
    private Long alumnoId;
    private String alumnoNombre;
    private LocalDateTime createdAt;
}