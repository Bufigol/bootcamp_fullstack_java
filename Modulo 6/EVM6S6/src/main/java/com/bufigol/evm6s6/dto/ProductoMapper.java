package com.bufigol.evm6s6.mapper;

import com.bufigol.evm6s6.dto.ProductoRequestDTO;
import com.bufigol.evm6s6.dto.ProductoResponseDTO;
import com.bufigol.evm6s6.modelo.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public Producto toEntity(ProductoRequestDTO dto) {
        return Producto.builder()
                .modelo(dto.getModelo())
                .marca(dto.getMarca())
                .descripcion(dto.getDescripcion())
                .build();
    }

    public ProductoResponseDTO toDto(Producto producto) {
        return ProductoResponseDTO.builder()
                .id(producto.getIdProducto())
                .modelo(producto.getModelo())
                .marca(producto.getMarca())
                .descripcion(producto.getDescripcion())
                .build();
    }
}