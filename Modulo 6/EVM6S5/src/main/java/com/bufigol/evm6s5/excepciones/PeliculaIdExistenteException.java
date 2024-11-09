package com.bufigol.evm6s5.excepciones;

public class PeliculaIdExistenteException extends PeliculaBaseException {
    public PeliculaIdExistenteException() {
        super(
                "Id ya registrada",
                "PELICULA_001",
                "Violación de constraint de unicidad en la tabla pelicula",
                409
        );
    }

    public String getSugerencia() {
        return "Intente con un ID diferente que no esté en uso";
    }
}