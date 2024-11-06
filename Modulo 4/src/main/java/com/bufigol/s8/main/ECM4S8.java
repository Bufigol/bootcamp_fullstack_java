package com.bufigol.s8.main;

import com.bufigol.s8.modelo.Alumno;
import com.bufigol.s8.modelo.Profesor;

import java.util.ArrayList;

public class ECM4S8 {

    public static void main(String[] args) {
        ArrayList<Profesor> profesores = cargarProfesores();
        ArrayList<Alumno> alumnos = cargarAlumnos(profesores);
        mostrarAlumnos(alumnos);
        mostrarProfesores(profesores);

    }

    private static void mostrarProfesores(ArrayList<Profesor> profesores) {
        for(Profesor p : profesores) {
            System.out.println(p.toString());
        }
    }

    private static void mostrarAlumnos(ArrayList<Alumno> alumnos) {
        for(Alumno a : alumnos) {
            System.out.println(a.toString());
        }
    }

    private static ArrayList<Alumno> cargarAlumnos(ArrayList<Profesor> profesores) {
        ArrayList<Alumno> alumnos = new ArrayList<>();
        alumnos.add(new Alumno("Primer Alumno","17.268.668-8", profesores.get(0),"Java"));
        alumnos.add(new Alumno("Segundo Alumno","17.268.668-8", profesores.get(1),"JavaScript"));
        alumnos.add(new Alumno("Tercer Alumno","17.268.668-8", profesores.get(2),"JavaScript"));
        alumnos.add( new Alumno("Cuarto Alumno","17.268.668-8", profesores.get(0),"Java"));
        return alumnos;
    }

    private static ArrayList<Profesor> cargarProfesores() {
        ArrayList<Profesor> profesores = new ArrayList<>();
        profesores.add(new Profesor("Primer Profesor","17.268.668-8","Java"));
        profesores.add(new Profesor("Profesor","17.268.668-8","JavaScript"));
        profesores.add(new Profesor("Otro Profe","17.268.668-8","JavaScript"));
        return profesores;
    }

}
