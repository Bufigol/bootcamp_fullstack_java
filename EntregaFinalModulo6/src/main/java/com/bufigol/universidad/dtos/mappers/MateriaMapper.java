package com.bufigol.universidad.dtos.mappers;

import com.bufigol.universidad.dtos.modelo.MateriaRequestDTO;
import com.bufigol.universidad.dtos.modelo.MateriaResponseDTO;
import com.bufigol.universidad.modelo.Materia;
import org.springframework.stereotype.Component;

@Component
public class MateriaMapper {

    public Materia toEntity(MateriaRequestDTO dto) {
        Materia materia = new Materia();
        materia.setNombre(dto.getNombre());
        return materia;
    }

    public MateriaResponseDTO toDto(Materia materia) {
        MateriaResponseDTO dto = new MateriaResponseDTO();
        dto.setId(materia.getId());
        dto.setNombre(materia.getNombre());
        dto.setCreatedAt(materia.getCreatedAt());

        if (materia.getAlumno() != null) {
            dto.setAlumnoId(materia.getAlumno().getId());
            dto.setAlumnoNombre(materia.getAlumno().getNombre());
        }

        return dto;
    }

    public void updateEntityFromDto(MateriaRequestDTO dto, Materia materia) {
        materia.setNombre(dto.getNombre());
    }
}