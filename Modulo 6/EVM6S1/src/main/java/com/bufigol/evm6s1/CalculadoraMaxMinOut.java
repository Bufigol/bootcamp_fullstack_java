package com.bufigol.evm6s1;


import lombok.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CalculadoraMaxMinOut implements ICalculadora {
    @NonNull
    private List<Alumno> alumnos;

    @Override
    public List<Alumno> calcularPromedios() {
        if (this.alumnos.isEmpty()){
            return this.alumnos;
        }
        for (Alumno a : alumnos) {
            if(a.getNotas().size() >= 3){
                float[] notas = new float[a.getNotas().size()];
                for (int i = 0; i < a.getNotas().size(); i++) {
                    notas[i] = a.getNotas().get(i);
                }
                int n = notas.length;
                boolean swapped;
                for (int i = 0; i < n - 1; i++) {
                    swapped = false;
                    for (int j = 0; j < n - 1 - i; j++) {
                        if (notas[j] > notas[j + 1]) {
                            float temp = notas[j];
                            notas[j] = notas[j + 1];
                            notas[j + 1] = temp;
                            swapped = true;
                        }
                    }
                    if (!swapped) {
                        break;
                    }
                }
                float promedio = 0;
                for(int i = 1; i < ( notas.length - 1 ) ; i++){
                    promedio += notas[i];
                }
                promedio = promedio / ( notas.length - 2 );
                a.setPromedio(promedio);
            } else{
                float[] notas = new float[a.getNotas().size()];
                for (int i = 0; i < a.getNotas().size(); i++) {
                    notas[i] = a.getNotas().get(i);
                }
                float promedio = 0;
                for(int i = 0; i < notas.length; i++){
                    promedio += notas[i];
                }
                a.setPromedio(promedio / notas.length);
            }


        }
        return this.alumnos;
    }
}