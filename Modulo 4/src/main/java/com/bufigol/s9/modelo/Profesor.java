package com.bufigol.s9.modelo;

import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;

public class Profesor extends Persona {
    private HashMap<Alumno, ArrayList<Tarea>> listaDeTareasAsignadas;

    public Profesor(String nombre, Date fechaNacimiento, HashMap<Alumno, ArrayList<Tarea>> listaDeTareasAsignadas) {
        super(nombre, fechaNacimiento);
        this.listaDeTareasAsignadas = listaDeTareasAsignadas;
    }

    public Profesor(String nombre, Date fechaNacimiento) {
        super(nombre, fechaNacimiento);
        this.listaDeTareasAsignadas = new HashMap<>();
    }

    public Profesor() {
        super();
    }

    public HashMap<Alumno, ArrayList<Tarea>> getListaDeTareasAsignadas() {
        return listaDeTareasAsignadas;
    }

    public void setListaDeTareasAsignadas(HashMap<Alumno, ArrayList<Tarea>> listaDeTareasAsignadas) {
        this.listaDeTareasAsignadas = listaDeTareasAsignadas;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Profesor{\n");
        sb.append("Nombre: ").append(this.getNombre()).append("\n");
        sb.append("Fecha de Nacimiento: ").append(this.getFechaNacimiento()).append("\n");
        sb.append("listaDeTareasAsignadas= ").append("\n");
        if (this.listaDeTareasAsignadas.isEmpty() || this.listaDeTareasAsignadas == null) {
            sb.append("Sin tareas asignadas").append("\n");
        }else{
            Set<Alumno> keys = this.listaDeTareasAsignadas.keySet();
            for (Alumno key : keys) {
                sb.append(key.toString()).append("Tiene las siguientes tareas: ").append("\n");
                ArrayList<Tarea> tareas = this.listaDeTareasAsignadas.get(key);
                for (Tarea tarea : tareas) {
                    sb.append(tarea.toString()).append("\n");
                }
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
