package com.bufigol.s10.main;

import com.bufigol.s10.modelo.DirectorTecnico;
import com.bufigol.s10.modelo.Jugador;
import com.bufigol.s10.modelo.Persona;

public class EVM4S10 {

    public static void main(String[] args) {
        Persona[]  equipo = cargaDeDatos();
        mostrarEqupo(equipo);
        mostrarToString(equipo);
    }

    private static void mostrarToString(Persona[] equipo) {
        for(Persona persona : equipo) {
            System.out.println(persona.toString());
        }
    }

    private static void mostrarEqupo(Persona[] equipo) {
        for(Persona persona : equipo) {
            if(persona instanceof DirectorTecnico) {
                System.out.println(persona.getNombre() + " es director tecnico");

            }
            if(persona instanceof Jugador) {
                System.out.println(persona.getNombre() + " es un jugador"+ " y su posicion es " + ((Jugador) persona).getPosicion());
            }
        }
    }

    private static Persona[] cargaDeDatos() {
        return new Persona[]{
                new DirectorTecnico("Juan", "Perez", 35, 5),
            new Jugador("Pedro", "Gonzalez", 25, 10, "Delantero"),
            new Jugador("Maria", "Lopez", 30, 7, "Delantero"),
            new Jugador("Ana", "Martinez", 28, 9, "Defensa"),
            new Jugador("Luis", "Garcia", 32, 11, "Defensa"),
            new Jugador("Carlos", "Hernandez", 27, 8, "Mediocampo"),
            new Jugador("Sandra", "Ramirez", 29, 6, "Mediocampo"),
            new Jugador("Pablo", "Sanchez", 26, 12, "Mediocampo"),
            new Jugador("Luisa", "Gutierrez", 24, 13, "Mediocampo"),
            new Jugador("Jorge", "Fernandez", 31, 14, "Mediocampo"),
            new Jugador("Carmen", "Ruiz", 23, 15, "Mediocampo"),
            new Jugador("Alberto", "Diaz", 22, 16, "Mediocampo")
        };
    }
}

