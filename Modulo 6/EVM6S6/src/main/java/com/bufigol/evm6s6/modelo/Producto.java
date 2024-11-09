package com.bufigol.evm6s6.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    @Column(name = "modelo", nullable = false, length = 100)
    private String modelo;

    @Column(name = "marca", nullable = false, length = 100)
    private String marca;

    @Column(name = "descripcion", columnDefinition = "TEXT", nullable = false)
    private String descripcion;
}