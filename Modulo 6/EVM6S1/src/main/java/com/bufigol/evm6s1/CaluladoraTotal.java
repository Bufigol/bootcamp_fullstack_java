package com.bufigol.evm6s1;

import lombok.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CaluladoraTotal implements ICalculadora {

    @NonNull private List<Alumno> alumnos;

    @Override
    public List<Alumno> calcularPromedios() {
        if (this.alumnos.isEmpty()){
            return this.alumnos;
        }
        for (Alumno a : this.alumnos) {
            if (a.getNotas() != null) {
                float promedio = 0;
                for (int i = 0; i < a.getNotas().size(); i++) {
                    promedio += a.getNotas().get(i);
                }
                promedio = promedio / a.getNotas().size();
                a.setPromedio(promedio);
            }
        }
        return this.alumnos;
    }
}
