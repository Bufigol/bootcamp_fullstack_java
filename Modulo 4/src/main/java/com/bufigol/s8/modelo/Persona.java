package com.bufigol.s8.modelo;

import java.util.Objects;

public abstract class Persona {
    private String nombre;
    private String identificador;

    public Persona(String nombre, String identificador) {
        this.nombre = nombre;
        this.identificador = identificador;
    }

    public Persona() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    @Override
    public String toString(){
        return "Persona: { nombre = "+nombre + " , identificador = " + identificador + "}";
    }
}
