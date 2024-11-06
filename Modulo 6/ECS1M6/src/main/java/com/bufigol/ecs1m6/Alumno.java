package com.bufigol.ecs1m6;

import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Alumno {


    @NonNull private String rut;
    @NonNull private String nombre;
    @NonNull private List<Integer> notas;
    private Float promedio;


}
