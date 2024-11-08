package com.bufigol.evm6s5.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pelicula {
    private Long id;
    private String titulo;
    private String director;
    private Integer anio;
    private Integer duracion;
}