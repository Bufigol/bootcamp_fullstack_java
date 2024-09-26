package com.bufigol.s17.ECM4S17.Model;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Cabina {

    private int capacidadPasajeros;
    private int numeroPuesrtas;
    private boolean puertasCerradas;
    private boolean buenEstado;

    public Cabina(int capacidadPasajeros, int numeroPuesrtas, boolean puertasCerradas, boolean buenEstado) {
        this.capacidadPasajeros = capacidadPasajeros;
        this.numeroPuesrtas = numeroPuesrtas;
        this.puertasCerradas = puertasCerradas;
        this.buenEstado = buenEstado;
    }

    /**
     * Default constructor
     */
    public Cabina() {
    }


    public void cerrarPuertas() {
        // TODO implement here
    }

    public int getCapacidadPasajeros() {
        return capacidadPasajeros;
    }

    public void setCapacidadPasajeros(int capacidadPasajeros) {
        this.capacidadPasajeros = capacidadPasajeros;
    }

    public int getNumeroPuesrtas() {
        return numeroPuesrtas;
    }

    public void setNumeroPuesrtas(int numeroPuesrtas) {
        this.numeroPuesrtas = numeroPuesrtas;
    }

    public boolean isPuertasCerradas() {
        return puertasCerradas;
    }

    public void setPuertasCerradas(boolean puertasCerradas) {
        this.puertasCerradas = puertasCerradas;
    }

    public boolean isBuenEstado() {
        return buenEstado;
    }

    public void setBuenEstado(boolean buenEstado) {
        this.buenEstado = buenEstado;
    }
}