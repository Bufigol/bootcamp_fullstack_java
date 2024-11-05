package com.bufigol.modelo;

public class Hijo extends Humano{
    private String cuenta;

    public Hijo(int patas, int peso, String nombre, int IQ) {
        super(patas, peso, nombre, IQ);
    }

    public Hijo(int patas, int peso, String nombre, int IQ, String cuenta) {
        super(patas, peso, nombre, IQ);
        this.cuenta = cuenta;
    }

    public Hijo() {
        super();
    }
}
