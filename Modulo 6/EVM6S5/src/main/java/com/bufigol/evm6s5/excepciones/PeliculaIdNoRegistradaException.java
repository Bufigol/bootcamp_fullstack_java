package com.bufigol.evm6s5.excepciones;


import lombok.Getter;

@Getter
public class PeliculaIdNoRegistradaException extends PeliculaBaseException {
    private final Long idInvalido;

    public PeliculaIdNoRegistradaException(Long id) {
        super(
                "Id no registrada",
                "PELICULA_003",
                String.format("Intento de operación con ID no existente: %d", id),
                404
        );
        this.idInvalido = id;
    }

    public String getSugerencia() {
        return "Verifique que el ID existe antes de realizar la operación";
    }
}