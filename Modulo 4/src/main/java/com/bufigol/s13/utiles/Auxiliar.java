package com.bufigol.s13.utiles;

import com.bufigol.s13.modelo.Alumno;
import com.bufigol.s13.modelo.Curso;
import com.bufigol.utils.ManejoDeArchvios;
import com.bufigol.utils.ManejoDeFechas;

import java.util.ArrayList;

public class Auxiliar {
    public static ArrayList<Alumno> cargarAlumnos() {
        ArrayList<Alumno> alumnosList = new ArrayList<>();
        ArrayList<String[]> lines = ManejoDeArchvios.readCSV("src/main/resources/listaAlumnos.csv", ";");
        for (String[] line : lines) {
            Alumno alumno = new Alumno(line[0], line[1], ManejoDeFechas.stringToDate(line[2]));
            alumnosList.add(alumno);
        }
        return alumnosList;
    }
    public static ArrayList<Curso> cargarCursos() {
        ArrayList<Curso> cursoList = new ArrayList<>();
        ArrayList<String[]> lines = ManejoDeArchvios.readCSV("src/main/resources/cursosCentroDeEstudios.csv", ",");
        for (String[] line : lines) {
            Curso curso = new Curso(line[0], line[1], line[2]);
            cursoList.add(curso);
        }
        return cursoList;
    }
    public static void añadirAlumnoAlArchivo(Alumno alumno) {
        String[] nuevoDato = {alumno.getNombre(), alumno.getRut(), ManejoDeFechas.dateToString(alumno.getFechaNacimiento())};
        ManejoDeArchvios.appendToCSV("src/main/resources/listaAlumnos.csv", ";", nuevoDato );
    }
    public static void añadirCursoAlArchivo(Curso curso) {
        String[] nuevoDato = {curso.getNombre(), curso.getCodigo(), curso.getDescripcion()};
        ManejoDeArchvios.appendToCSV("src/main/resources/cursosCentroDeEstudios.csv", ",", nuevoDato );
    }
}
