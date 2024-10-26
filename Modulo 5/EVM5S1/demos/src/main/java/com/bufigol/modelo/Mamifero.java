package com.bufigol.modelo;

public abstract class Mamifero {
    private int patas;
    private int peso;

    public Mamifero(int patas, int peso) {
        this.patas = patas;
        this.peso = peso;
    }

    public Mamifero() {
    }

    public int getPatas() {
        return patas;
    }

    public void setPatas(int patas) {
        this.patas = patas;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }
    public void comer(){
        System.out.println("Mamifero come");
    }
}
