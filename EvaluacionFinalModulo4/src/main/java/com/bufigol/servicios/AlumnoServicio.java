package com.bufigol.servicios;

import com.bufigol.modelo.Alumno;
import com.bufigol.modelo.Materia;
import com.bufigol.utils.Validaciones;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlumnoServicio {
    private Map<String, Alumno> listaAlumnos;

    public AlumnoServicio(Map<String, Alumno> listaAlumnos) {
        this.listaAlumnos = listaAlumnos;
    }

    public AlumnoServicio() {
        this.listaAlumnos = new HashMap<>();
    }

    public void crearAlumno(Alumno alumno) {
        this.listaAlumnos.put(alumno.getRut(), alumno);
    }

    public void agregarMateria(@NotNull Alumno alumno, Materia currentMate) {
        this.listaAlumnos.get(alumno.getRut()).getMaterias().add(currentMate);
    }

    public List<Materia> materiasPorAlumno(String input) {
        // Primero, comprobamos si es un RUT válido
        if (Validaciones.isValidRut(input)) {
            Alumno alumno = listaAlumnos.get(input);
            if (alumno != null) {
                return alumno.getMaterias();
            }
        }

        // Si no es un RUT válido o no se encontró el alumno, buscamos por nombre y apellido
        String inputLowerCase = input.toLowerCase();
        for (Alumno alumno : listaAlumnos.values()) {
            String nombreCompleto = (alumno.getNombre() + " " + alumno.getApellido()).toLowerCase();
            if (nombreCompleto.equals(inputLowerCase)) {
                return alumno.getMaterias();
            }
        }

        // Si no se encuentra ningún alumno, retornamos una lista vacía
        return new ArrayList<>();
    }

    public Map<String, Alumno> listarAlumnos() {
        if (!this.listaAlumnos.isEmpty()) {
            return this.listaAlumnos;
        }else {
            return new HashMap<>();
        }
    }

    public Alumno buscarAlumnoPorRut(String rut) {
        return this.listaAlumnos.get(rut);
    }
}
