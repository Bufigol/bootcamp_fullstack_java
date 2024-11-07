package com.bufigol.evm6s3.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Producto {
    private int id;
    private String nombre;
    private String descripcion;
    private Integer precio;
    private Integer stock;
}