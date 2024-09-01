package com.bufigol.s8.modelo;

public class Vestuario extends Producto {
    private String talla;
    private String color;

    public Vestuario(String nombre, int codigo, float precio,int cantidad, String talla, String color) {
        super(nombre, codigo, precio,cantidad);
        this.talla = talla;
        this.color = color;
    }

    public Vestuario(String nombre, int codigo, float precio,int cantidad) {
        super(nombre, codigo, precio, cantidad);
    }

    public Vestuario(String talla, String color) {
        super();
        this.talla = talla;
        this.color = color;
    }

    public Vestuario() {
        super();
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Vestuario { nombre = " + this.getNombre() +
                ", codigo = " + this.getCodigo() +
                ", precio = " + this.getPrecio() +
                ", talla = " + talla +
                ", color = " + color + "}";
    }
}
