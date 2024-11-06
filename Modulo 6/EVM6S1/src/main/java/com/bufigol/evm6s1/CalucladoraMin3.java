package com.bufigol.evm6s1;

import lombok.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CalucladoraMin3 implements ICalculadora {

    @NonNull
    private List<Alumno> alumnos;

    @Override
    public List<Alumno> calcularPromedios() {
        if(this.alumnos.isEmpty()){
            return this.alumnos;
        }
        for (Alumno a : this.alumnos) {
            int counter = 0;
            if (a.getNotas() != null) {
                float promedio = 0;
                for (int i = 0; i < a.getNotas().size(); i++) {
                    if(a.getNotas().get(i) >= 3){
                        promedio += a.getNotas().get(i);
                        counter++;
                    }
                }
                promedio = promedio / counter;
                a.setPromedio(promedio);
            }
        }
        return this.alumnos;
    }
}
