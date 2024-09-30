package com.bufigol.s19.modelo;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Jugador {

    private String nombre;
    private int edad;
    private int numero;

    public Jugador() {
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull Jugador crearJugador(String nombre, int edad, int numero) {
        Jugador out = new Jugador();
        out.setEdad(edad);
        out.setNombre(nombre);
        out.setNumero(numero);
        return out;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}
