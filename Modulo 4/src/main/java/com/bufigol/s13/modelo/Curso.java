package com.bufigol.s13.modelo;

public class Curso {
    private String nombre;
    private String codigo;
    private String descripcion;

    public Curso(String nombre, String codigo, String descripcion) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public Curso() {
        this.nombre = "";
        this.codigo = "";
        this.descripcion = "";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Curso{\n" +
                "nombre = " + nombre + '\n' +
                "codigo = " + codigo + '\n' +
                "descripcion= " + descripcion + '\n' +
                '}';
    }
}
