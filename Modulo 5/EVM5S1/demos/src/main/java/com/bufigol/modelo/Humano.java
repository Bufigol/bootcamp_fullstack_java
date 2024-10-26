package com.bufigol.modelo;

public class Humano extends Mamifero {

    private int IQ;
    private String nombre;

    public Humano(int patas, int peso) {
        super(patas, peso);
    }

    public Humano(int patas, int peso, String nombre, int IQ) {
        super(patas, peso);
        this.nombre = nombre;
        this.IQ = IQ;
    }

    public Humano() {
        super();
    }

    @Override
    public void comer(){
        System.out.println("Humano come");
    }
}
