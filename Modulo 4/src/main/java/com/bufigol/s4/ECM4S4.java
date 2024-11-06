package com.bufigol.s4;

public class ECM4S4 {

    public static void main(String[] args) {
        Persona[] listaDePersonas = {new Persona("Juan", 25, 80, 1.75f),
        new Persona("Pedro", 16, 75, 1.85f),
        new Persona("Maria", 18, 60, 1.60f),
        new Persona("Luis", 17, 90, 1.80f),
        new Persona("Ana", 28, 85, 1.65f)};

        for (Persona persona : listaDePersonas) {
            persona.getImcInfo();
            persona.esMayorDeEdad();
            System.out.println(persona);
        }

    }
}
