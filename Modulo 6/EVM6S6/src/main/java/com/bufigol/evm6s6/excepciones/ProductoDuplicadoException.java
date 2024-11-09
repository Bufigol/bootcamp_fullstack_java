package com.bufigol.evm6s6.excepciones;

public class ProductoDuplicadoException extends RuntimeException {
    public ProductoDuplicadoException(String message) {
        super(message);
    }

    public ProductoDuplicadoException(String modelo, String marca) {
        super("Ya existe un producto con modelo: " + modelo + " y marca: " + marca);
    }
}
