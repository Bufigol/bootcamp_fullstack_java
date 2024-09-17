package com.bufigol.modelo;

import java.util.List;

public class Materia {
    private MateriasEnum nombre;
    private List<Float> notas;

    public Materia(MateriasEnum nombre, List<Float> notas) {
        this.nombre = nombre;
        this.notas = notas;
    }

    public MateriasEnum getNombre() {
        return nombre;
    }

    public String getNombre(MateriasEnum nombre) {
        return nombre.toString();
    }

    public void setNombre(MateriasEnum nombre) {
        this.nombre = nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = MateriasEnum.valueOf(nombre);
    }

    public List<Float> getNotas() {
        return notas;
    }

    public void setNotas(List<Float> notas) {
        this.notas = notas;
    }
}
