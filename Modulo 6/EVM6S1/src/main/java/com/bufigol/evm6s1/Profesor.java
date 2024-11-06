package com.bufigol.evm6s1;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Profesor {
    private ICalculadora calculadora;

    public void mostrarNotas(){
        if(this.calculadora != null){
            List<Alumno> alumnos = this.calculadora.calcularPromedios();
            if(alumnos != null){
                for(Alumno alumno : alumnos){
                    System.out.println(alumno.getNombre() + " - " + alumno.getPromedio());
                }
            }
        }
    }
}
