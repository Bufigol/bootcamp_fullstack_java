package com.bufigol.s8.modelo;

import java.util.ArrayList;

public class Alumno extends  Persona{
    private Profesor profesor;
    private String hobby;
    private ArrayList<Tarea> listaDeTareas;

    public Alumno(String nombre, String identificador, Profesor profesor, String hobby) {
        super(nombre, identificador);
        this.profesor = profesor;
        this.hobby = hobby;
        this.listaDeTareas =  new ArrayList<Tarea>();
    }

    public Alumno(String nombre, String identificador, Profesor profesor, String hobby, ArrayList<Tarea> listaDeTareas) {
        super(nombre, identificador);
        this.profesor = profesor;
        this.hobby = hobby;
        this.listaDeTareas = listaDeTareas;
    }

    public Alumno() {
        super();
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public ArrayList<Tarea> getListaDeTareas() {
        return listaDeTareas;
    }

    public void setListaDeTareas(ArrayList<Tarea> listaDeTareas) {
        this.listaDeTareas = listaDeTareas;
    }

    public void mostrarListaDeTareasRealizadas() {
        for (Tarea listaDeTarea : this.listaDeTareas) {
            if (listaDeTarea.isRealizada()) {
                System.out.println(listaDeTarea.toString());
            }
        }
    }

    public Tarea[] listaDeTareasRealizadas(){
        int contador = 0;
        for (Tarea listaDeTarea : this.listaDeTareas) {
            if (listaDeTarea.isRealizada()) {
                contador++;
            }
        }
        Tarea[] tareas = new Tarea[contador];
        contador = 0;
        for (Tarea listaDeTarea : this.listaDeTareas) {
            if (listaDeTarea.isRealizada()) {
                tareas[contador] = listaDeTarea;
                contador++;
            }
        }
        return tareas;
    }

    public void realizarTarea(Tarea tarea) {
        int contador = 0;
        boolean realizada = false;
        do{
            if(this.listaDeTareas.get(contador).equals(tarea)) {
                this.listaDeTareas.get(contador).setRealizada(true);
                realizada = true;
            }
            contador++;
        }while (!realizada && contador < this.listaDeTareas.size());
        if(!realizada) {
            tarea.setRealizada(true);
            this.listaDeTareas.add(tarea);
        }
    }

    @Override
    public String toString() {
        if(!this.listaDeTareas.isEmpty()) {
            String out = "Alumno { nombre: " + this.getNombre() +
                    ", identificador: " + this.getIdentificador() +
                    ", Profesor: " + this.profesor.toString() +
                    ", hobby: " + this.getHobby() +
                    ", Lista de Tareas: ";
            for (Tarea listaDeTarea : this.listaDeTareas) {
                out = out.concat(listaDeTarea.toString() + "\n");
            }
            return out;
        }else{
            return "Alumno { nombre: " + this.getNombre() +
                    ", identificador: " + this.getIdentificador() +
                    ", Profesor: " + this.profesor.toString() +
                    ", hobby: " + this.getHobby();
        }

    }
}
