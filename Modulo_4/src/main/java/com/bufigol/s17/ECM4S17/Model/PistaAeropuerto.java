package com.bufigol.s17.ECM4S17.Model;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class PistaAeropuerto {
    private boolean dispoible;

    public PistaAeropuerto(boolean dispoible) {
        this.dispoible = dispoible;
    }

    /**
     * Default constructor
     */
    public PistaAeropuerto() {
    }

    public void actualizarDisponiblidad() {
        if (dispoible) {
            System.out.println("Pista disponible");
            dispoible = false;
        } else {
            System.out.println("Pista no disponible");
            dispoible = true;
        }
    }

    public boolean isDispoible() {
        return dispoible;
    }

    public void setDispoible(boolean dispoible) {
        this.dispoible = dispoible;
    }
}