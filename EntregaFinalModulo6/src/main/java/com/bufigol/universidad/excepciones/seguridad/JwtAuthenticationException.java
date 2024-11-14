package com.bufigol.universidad.excepciones.seguridad;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

import java.io.Serial;

public class JwtAuthenticationException extends AuthenticationException{
    @Serial
    private static final long serialVersionUID = -6845804968991690039L;

    @Getter
    private final String token;
    private final String message;

    public JwtAuthenticationException(String message) {
        super(message);
        this.token = null;
        this.message = message;
    }

    public JwtAuthenticationException(String token, String message) {
        super(message);
        this.token = token;
        this.message = message;
    }

    public JwtAuthenticationException(String token, String message, Throwable cause) {
        super(message, cause);
        this.token = token;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
