package com.bufigol.s2;

public class Producto {

    private double precioUnitario;
    private int cantidad;
    private double total;

    Producto(double precioUnitario, int cantidad) {
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.total = precioUnitario * cantidad;
    }

    public double getPrecioUnitario() {
        return this.precioUnitario;
    }
    public int getCantidad() {
        return this.cantidad;
    }
    public double getTotal() {
        return this.total;
    }
}
