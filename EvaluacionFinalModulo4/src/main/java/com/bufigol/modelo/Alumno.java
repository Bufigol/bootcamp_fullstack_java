package com.bufigol.modelo;

import java.util.List;
import java.util.Objects;

public class Alumno {
    private String rut;
    private String nombre;
    private String apellido;
    private String dirección;
    private List<Materia> materias;

    public Alumno(String rut, String nombre, String apellido, String dirección, List<Materia> materias) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dirección = dirección;
        this.materias = materias;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDirección() {
        return dirección;
    }

    public void setDirección(String dirección) {
        this.dirección = dirección;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }

    @Override
    public String toString() {
        return "Alumno{" + "rut='" + rut + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dirección='" + dirección + '\'' +
                ", materias=" + materias +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alumno alumno = (Alumno) o;
        return Objects.equals(getRut(), alumno.getRut()) && Objects.equals(getNombre(), alumno.getNombre()) && Objects.equals(getApellido(), alumno.getApellido()) && Objects.equals(getDirección(), alumno.getDirección()) && Objects.equals(getMaterias(), alumno.getMaterias());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getRut());
        result = 31 * result + Objects.hashCode(getNombre());
        result = 31 * result + Objects.hashCode(getApellido());
        result = 31 * result + Objects.hashCode(getDirección());
        result = 31 * result + Objects.hashCode(getMaterias());
        return result;
    }
}
