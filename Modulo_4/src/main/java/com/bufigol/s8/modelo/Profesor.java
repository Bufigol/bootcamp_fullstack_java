package com.bufigol.s8.modelo;

public class Profesor extends Persona {
    private String especialidad;

    public Profesor(String nombre, String identificador, String especialidad) {
        super(nombre, identificador);
        this.especialidad = especialidad;
    }

    public Profesor(String especialidad) {
        super("", "");
        this.especialidad = especialidad;
    }

    public Profesor() {
        super();
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    @Override
    public String toString() {
        return "Profesor { nombre = " + this.getNombre() +
                ", identificador = " + this.getIdentificador() +
                ", especialidad = " + this.especialidad + "}";
    }
}
