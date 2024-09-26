package com.bufigol.s17.ECM4S17.Model;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Motores {

    private boolean tieneCombustible;
    private boolean buenEstado;

    public Motores(boolean tieneCombustible, boolean buenEstado) {
        this.tieneCombustible = tieneCombustible;
        this.buenEstado = buenEstado;
    }

    /**
     * Default constructor
     */
    public Motores() {
        this.tieneCombustible = false;
        this.buenEstado = false;
    }


    public void cargarCombustible() {
       if(this.tieneCombustible) {
           System.out.println("Ya tiene combustible");
       }else {
           this.tieneCombustible = true;
           System.out.println("Combustible cargado");
       }
    }

    public boolean isTieneCombustible() {
        return tieneCombustible;
    }

    public void setTieneCombustible(boolean tieneCombustible) {
        this.tieneCombustible = tieneCombustible;
    }

    public boolean isBuenEstado() {
        return buenEstado;
    }

    public void setBuenEstado(boolean buenEstado) {
        this.buenEstado = buenEstado;
    }
}