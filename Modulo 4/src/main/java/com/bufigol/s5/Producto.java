package com.bufigol.s5;

public class Producto {
    private String nombre;
    private int valor;
    private String descripcion;

    public Producto(String nombre, int valor, String descripcion) {
        this.nombre = nombre;
        this.valor = valor;
        this.descripcion = descripcion;
    }

    public Producto() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre + '\'' +
                ", valor=" + valor +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
    public boolean esNuevo(){
        return (this.nombre == null && this.valor == 0 && this.descripcion == null) || (this.nombre == "" && this.valor == 0 && this.descripcion == "");
    }
}
