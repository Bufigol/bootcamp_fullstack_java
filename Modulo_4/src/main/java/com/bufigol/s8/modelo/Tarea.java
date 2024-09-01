package com.bufigol.s8.modelo;

public class Tarea {

    private String descripcion;
    private Profesor profesor;
    private boolean realizada;

    public Tarea(String descripcion, Profesor profesor) {
        this.descripcion = descripcion;
        this.profesor = profesor;
        this.realizada = false;
    }

    public Tarea(String descripcion, Profesor profesor, boolean realizada) {
        this.descripcion = descripcion;
        this.profesor = profesor;
        this.realizada = realizada;
    }

    public Tarea() {
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public boolean isRealizada() {
        return realizada;
    }

    public void setRealizada(boolean realizada) {
        this.realizada = realizada;
    }

    @Override
    public String toString() {
        return """
                Tarea{
                    descripcion='$descripcion',
                    profesor=$profesor,
                    realizada=$realizada
                }""";
    }
}
