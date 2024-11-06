package com.bufigol.ecs1m6;

import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Calculadora implements ICalculadora {
    @NonNull private List<Alumno> alumnos;

    @Override
    public List<Alumno> calcularPromedios() {
        if (!this.alumnos.isEmpty()){
            for (Alumno a : this.alumnos) {
                List<Integer> notas = a.getNotas();
                Float promedio = 0f;
                for (Integer n : notas) {
                    promedio += n;
                }
                promedio /= notas.size();
                a.setPromedio(promedio);
            }
            return this.alumnos;
        }else{
            return List.of();
        }

    }
}
