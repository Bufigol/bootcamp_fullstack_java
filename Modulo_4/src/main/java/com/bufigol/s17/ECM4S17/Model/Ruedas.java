package com.bufigol.s17.ECM4S17.Model;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Ruedas {
    private boolean infladas;
    private boolean buenEstado;

    public Ruedas(boolean infladas, boolean buenEstado) {
        this.infladas = infladas;
        this.buenEstado = buenEstado;
    }

    /**
     * Default constructor
     */
    public Ruedas() {
    }

    public void inflar() {
        if (infladas) {
            System.out.println("Las ruedas ya estaban infladas");
        } else {
            infladas = true;
            System.out.println("Las ruedas han sido infladas");
        }
    }

    public boolean isInfladas() {
        return infladas;
    }

    public void setInfladas(boolean infladas) {
        this.infladas = infladas;
    }

    public boolean isBuenEstado() {
        return buenEstado;
    }

    public void setBuenEstado(boolean buenEstado) {
        this.buenEstado = buenEstado;
    }
}