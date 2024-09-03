package com.bufigol.s10.modelo;

public abstract class Persona {
    private String nombre;
    private String apellidos;
    private int edad;

    public Persona(String nombre, String apellidos, int edad) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
    }

    public Persona() {
    }

    public void hablar(){

    }

    public void celebrar(){

    }

    public void esAmonestado(int tipoTarjeta){
        if(tipoTarjeta == 1){
            System.out.println("Es amonestado con tarjera amarilla");
        }
        else if(tipoTarjeta == 2){
            System.out.println("Es amonestado con tarjera roja");
        }else {
            System.out.println("No es amonestado");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "Persona{" + "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", edad=" + edad +
                '}';
    }
}
