package com.bufigol.s8.modelo;

public abstract class Producto {
    private String nombre;
    private int codigo;
    private float precio;
    private int cantidad;

    public Producto(String nombre, int codigo, float precio, int cantidad) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.precio = precio;
        this.cantidad = Math.max(cantidad, 0);
    }

    public Producto() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Producto { nombre = " + this.nombre +
                " , codigo = " + this.codigo +
                " , precio = " + this.precio +
                " , cantidad = " + this.cantidad +" }";
    }
}
