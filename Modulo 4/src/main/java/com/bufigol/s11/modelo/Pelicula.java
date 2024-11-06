package com.bufigol.s11.modelo;

public class Pelicula {
    private String titulo;
    private int añoDeEstreno;
    private Boolean isArrendada;

    public Pelicula(String titulo, int añoDeEstreno) {
        this.titulo = titulo;
        this.añoDeEstreno = añoDeEstreno;
        this.isArrendada = false;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAñoDeEstreno() {
        return añoDeEstreno;
    }

    public void setAñoDeEstreno(int añoDeEstreno) {
        this.añoDeEstreno = añoDeEstreno;
    }

    public Boolean getArrendada() {
        return isArrendada;
    }

    public void setArrendada(Boolean arrendada) {
        isArrendada = arrendada;
    }

    @Override
    public String toString() {
        return "Pelicula{" + "titulo='" + titulo + '\'' +
                ", añoDeEstreno=" + añoDeEstreno +
                ", ¿Esta arrendada? =" + isArrendada +
                '}';
    }
}
