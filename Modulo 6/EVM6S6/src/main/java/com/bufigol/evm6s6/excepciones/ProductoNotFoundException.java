package com.bufigol.evm6s6.excepciones;

public class ProductoNotFoundException extends RuntimeException {
    public ProductoNotFoundException(String message) {
        super(message);
    }

    public ProductoNotFoundException(Long id) {
        super("No se encontró el producto con ID: " + id);
    }
}