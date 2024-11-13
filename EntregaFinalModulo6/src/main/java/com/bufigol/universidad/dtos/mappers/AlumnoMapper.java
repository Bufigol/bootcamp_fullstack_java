package com.bufigol.universidad.dtos.mappers;

import com.bufigol.universidad.dtos.modelo.AlumnoRequestDTO;
import com.bufigol.universidad.dtos.modelo.AlumnoResponseDTO;
import com.bufigol.universidad.modelo.Alumno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AlumnoMapper {

    @Autowired
    private MateriaMapper materiaMapper;

    public Alumno toEntity(AlumnoRequestDTO dto) {
        Alumno alumno = new Alumno();
        // Normalizar el RUT antes de guardarlo
        alumno.setRut(normalizarRut(dto.getRut()));
        alumno.setNombre(dto.getNombre());
        alumno.setDireccion(dto.getDireccion());
        return alumno;
    }
    public AlumnoResponseDTO toDto(Alumno alumno) {
        AlumnoResponseDTO dto = new AlumnoResponseDTO();
        dto.setId(alumno.getId());
        dto.setRut(alumno.getRut());
        dto.setNombre(alumno.getNombre());
        dto.setDireccion(alumno.getDireccion());
        dto.setCreatedAt(alumno.getCreatedAt());

        if (alumno.getMateriaList() != null) {
            dto.setMaterias(alumno.getMateriaList().stream()
                    .map(materiaMapper::toDto)
                    .collect(Collectors.toSet()));
        }

        return dto;
    }

    public void updateEntityFromDto(AlumnoRequestDTO dto, Alumno alumno) {
        alumno.setRut(dto.getRut());
        alumno.setNombre(dto.getNombre());
        alumno.setDireccion(dto.getDireccion());
    }

    // Método para normalizar el formato del RUT
    private String normalizarRut(String rut) {
        if (rut == null) return null;
        return rut.replace(".", "").replace("-", "").toUpperCase();
    }
}