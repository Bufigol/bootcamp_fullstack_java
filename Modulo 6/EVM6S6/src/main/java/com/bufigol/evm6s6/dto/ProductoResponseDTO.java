package com.bufigol.evm6s6.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDTO {
    private Long id;
    private String modelo;
    private String marca;
    private String descripcion;
}