package com.bufigol.s17.ECM4S17.Model;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Alas {
    private String largoEnMetros;
    private boolean buenEstado;

    public Alas(String largoEnMetros, boolean buenEstado) {
        this.largoEnMetros = largoEnMetros;
        this.buenEstado = buenEstado;
    }

    /**
     * Default constructor
     */
    public Alas() {
    }

    public void pruebaAlas() {
        if (this.buenEstado) {
            System.out.println("Alas buenas");
        }else {
            System.out.println("Alas malas");
            this.buenEstado = true;
            System.out.println("Alas arregladas");
        }
    }

    public String getLargoEnMetros() {
        return largoEnMetros;
    }

    public void setLargoEnMetros(String largoEnMetros) {
        this.largoEnMetros = largoEnMetros;
    }

    public boolean isBuenEstado() {
        return buenEstado;
    }

    public void setBuenEstado(boolean buenEstado) {
        this.buenEstado = buenEstado;
    }
}