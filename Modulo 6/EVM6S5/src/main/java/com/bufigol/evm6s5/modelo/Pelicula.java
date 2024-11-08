package com.bufigol.evm6s5.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Pelicula {
    private Long id;
    private String titulo;
    private String director;
    private Integer anio;
    private Integer duracion;
}
