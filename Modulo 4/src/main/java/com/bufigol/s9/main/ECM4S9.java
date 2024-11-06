package com.bufigol.s9.main;

import com.bufigol.s9.modelo.Alumno;
import com.bufigol.s9.modelo.Persona;
import com.bufigol.s9.modelo.Profesor;

import java.util.ArrayList;
import java.util.Date;


public class ECM4S9 {

    public static void main(String[] args) {
        ArrayList<Persona> listaDePersonas = cargarDatos();
        mostrarDatos(listaDePersonas);
        mostrarSuperClase(listaDePersonas);
        mostrarDetalles(listaDePersonas);
    }

    private static void mostrarDetalles(ArrayList<Persona> listaDePersonas) {
        for (Persona persona : listaDePersonas) {
            if(persona instanceof Alumno alumno) {
                System.out.println(alumno.toString());
            }
            if(persona instanceof Profesor profes) {
                System.out.println(profes.toString());
            }
        }
    }

    private static void mostrarSuperClase(ArrayList<Persona> listaDePersonas) {
        for (Persona persona : listaDePersonas) {
            System.out.println(persona.getClass().getSuperclass());
        }
    }

    private static void mostrarDatos(ArrayList<Persona> listaDePersonas) {
        for (Persona persona : listaDePersonas) {
            if(persona instanceof Alumno alumno) {
                System.out.println(alumno.getNombre() + " es un Alumno");
            }
            if(persona instanceof Profesor profes) {
                System.out.println(profes.getNombre() + " es un Profesor");
            }
        }
    }

    private static ArrayList<Persona> cargarDatos() {
        ArrayList<Persona> listaDePersonas = new ArrayList<>();
        listaDePersonas.add(new Profesor("Juan", new Date(620768790000L)));
        listaDePersonas.add(new Profesor("Pablo", new Date(614751335000L)));
        listaDePersonas.add(new Alumno("Andres", new Date(930197735000L)));
        listaDePersonas.add(new Alumno("Sebastian", new Date(935511335000L)));
        return listaDePersonas;
    }
}
