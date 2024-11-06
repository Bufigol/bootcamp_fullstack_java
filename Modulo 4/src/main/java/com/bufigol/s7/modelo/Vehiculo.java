package com.bufigol.s7.modelo;

public abstract class Vehiculo {
    private int numeroDeRuedas;
    private int cantidadDeVentanas;
    private boolean encendido;

    public Vehiculo(int numeroDeRuedas, int cantidadDeVentanas) {
        this.numeroDeRuedas = numeroDeRuedas;
        this.cantidadDeVentanas = cantidadDeVentanas;
        this.encendido = false;
    }

    public Vehiculo() {
        this.encendido = false;
    }

    public  abstract void encender();

    public  abstract void apagar();

    public int getNumeroDeRuedas() {
        return numeroDeRuedas;
    }

    public void setNumeroDeRuedas(int numeroDeRuedas) {
        this.numeroDeRuedas = numeroDeRuedas;
    }

    public int getCantidadDeVentanas() {
        return cantidadDeVentanas;
    }

    public void setCantidadDeVentanas(int cantidadDeVentanas) {
        this.cantidadDeVentanas = cantidadDeVentanas;
    }

    protected boolean isEncendido() {
        return encendido;
    }

    protected void setEncendido(boolean encendido) {
        this.encendido = encendido;
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "numeroDeRuedas=" + numeroDeRuedas +
                ", cantidadDeVentanas=" + cantidadDeVentanas +
                ", encendido=" + encendido +
                '}';
    }
}
