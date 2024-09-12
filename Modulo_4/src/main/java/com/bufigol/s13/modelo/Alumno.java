package com.bufigol.s13.modelo;

import com.bufigol.utils.ComprobacionesVarias;
import com.bufigol.utils.ManejoDeFechas;

import java.util.Date;

public class Alumno {

    private String nombre;
    private String rut;
    private Date fechaNacimiento;

    public Alumno(String nombre, String rut, Date fechaNacimiento) {
        this.nombre = nombre;
        if(ComprobacionesVarias.isValidRut(rut)){
            this.rut = rut;
        }
        else{
            throw new IllegalArgumentException("Rut no valido");
        }
        this.fechaNacimiento = fechaNacimiento;
    }

    public Alumno(String nombre, String rut, String fechaNacimiento) {
        this.nombre = nombre;
        if(ComprobacionesVarias.isValidRut(rut)){
            this.rut = rut;
        }
        else{
            throw new IllegalArgumentException("Rut no valido");
        }
        this.fechaNacimiento = ManejoDeFechas.stringToDate(fechaNacimiento);
    }

    public Alumno() {
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        if(ComprobacionesVarias.isValidRut(rut)){
            this.rut = rut;
        }
        else{
            throw new IllegalArgumentException("Rut no valido");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "nombre='" + nombre + '\'' +
                ", rut='" + rut + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                '}';
    }
}
