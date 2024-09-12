package com.bufigol.s14.modelo;

import java.util.Objects;

public class Socio {
    private String nombre;
    private int edad;
    private String cargo;

    public Socio(String nombre, int edad, String cargo) {
        this.nombre = nombre;
        this.edad = edad;
        this.cargo = cargo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }
        Socio socio = (Socio) o;
        boolean out = false;
        if( getEdad() == socio.getEdad() ){
            if( getCargo().replace(" ", "").equalsIgnoreCase( socio.getCargo() ) ){
                if(getNombre().replace(" ", "").equalsIgnoreCase( socio.getNombre() ) ){
                    out = true;
                }
            }
        };
        return  out;
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getNombre());
        result = 31 * result + getEdad();
        result = 31 * result + Objects.hashCode(getCargo());
        return result;
    }

    @Override
    public String toString() {
        return "Socio{" + "nombre=" + nombre + ", edad=" + edad + ", cargo=" + cargo + '}';
    }
}
