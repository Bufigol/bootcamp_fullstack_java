package com.bufigol.s9.modelo;

import com.bufigol.s8.modelo.Tarea;

import java.util.ArrayList;
import java.util.Date;

public class Alumno extends Persona {
    private ArrayList<Tarea> tareasPendientes;
    private ArrayList<Tarea> tareasRealizadas;

    public Alumno(String nombre, Date fechaNacimiento, ArrayList<Tarea> tareasPendientes, ArrayList<Tarea> tareasRealizadas) {
        super(nombre, fechaNacimiento);
        this.tareasPendientes = tareasPendientes;
        this.tareasRealizadas = tareasRealizadas;
    }

    public Alumno(ArrayList<Tarea> tareasPendientes, ArrayList<Tarea> tareasRealizadas) {
        this.tareasPendientes = tareasPendientes;
        this.tareasRealizadas = tareasRealizadas;
    }

    public Alumno(String nombre, Date fechaNacimiento) {
        super(nombre, fechaNacimiento);
        this.tareasPendientes = new ArrayList<>();
        this.tareasRealizadas = new ArrayList<>();
    }

    public Alumno() {
        super();
        this.tareasPendientes = new ArrayList<>();
        this.tareasRealizadas = new ArrayList<>();
    }

    public ArrayList<Tarea> getTareasPendientes() {
        return tareasPendientes;
    }

    public void setTareasPendientes(ArrayList<Tarea> tareasPendientes) {
        this.tareasPendientes = tareasPendientes;
    }

    public ArrayList<Tarea> getTareasRealizadas() {
        return tareasRealizadas;
    }

    public void setTareasRealizadas(ArrayList<Tarea> tareasRealizadas) {
        this.tareasRealizadas = tareasRealizadas;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Alumno{").append("\n");
        sb.append("Nombre: ").append(this.getNombre()).append("\n");
        sb.append("Fecha de Nacimiento: ").append(this.getFechaNacimiento()).append("\n");
        if( ( this.tareasPendientes.isEmpty() ) &&
            ( this.tareasRealizadas.isEmpty()) ) {
            sb.append('}');
            return sb.toString();
        }else{
            if( !this.tareasPendientes.isEmpty()) {
                for(Tarea tarea : this.tareasPendientes) {
                    sb.append(tarea.toString()).append("\n");
                }
            }
            if( !this.tareasRealizadas.isEmpty()) {
                for(Tarea tarea : this.tareasRealizadas) {
                    sb.append(tarea.toString()).append("\n");
                }
            }
            sb.append('}');
            return sb.toString();
        }
    }
}
