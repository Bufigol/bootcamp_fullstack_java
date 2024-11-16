package com.bufigol.universidad.excepciones.seguridad;

import lombok.Getter;

@Getter
public class JwtInvalidTokenException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final String token;

    public JwtInvalidTokenException(String token, String message) {
        super(message);
        this.token = token;
    }

}