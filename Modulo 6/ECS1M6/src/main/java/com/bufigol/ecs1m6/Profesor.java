package com.bufigol.ecs1m6;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Profesor {

    @NonNull private Calculadora calculadora;

    public void mostrarAlumnosConPromedios(){
        List<Alumno> alumnos = this.calculadora.calcularPromedios();
        for(Alumno a : alumnos){
            System.out.println("Alumno: " + a.getNombre() + " Promedio: " + a.getPromedio());
        }
    }
}
