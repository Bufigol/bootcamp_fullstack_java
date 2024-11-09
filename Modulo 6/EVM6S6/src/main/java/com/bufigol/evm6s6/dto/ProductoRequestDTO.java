package com.bufigol.evm6s6.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequestDTO {
    @NotBlank(message = "El modelo no puede estar vacío")
    @Size(max = 100, message = "El modelo no puede tener más de 100 caracteres")
    private String modelo;

    @NotBlank(message = "La marca no puede estar vacía")
    @Size(max = 100, message = "La marca no puede tener más de 100 caracteres")
    private String marca;

    @Size(max = 500, message = "La descripción no puede tener más de 500 caracteres")
    private String descripcion;
}