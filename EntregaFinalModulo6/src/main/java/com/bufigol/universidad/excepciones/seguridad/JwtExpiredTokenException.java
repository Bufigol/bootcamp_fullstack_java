package com.bufigol.universidad.excepciones.seguridad;

import java.io.Serial;

import static com.bufigol.universidad.seguridad.constantes.ConstantesSeguridad.TOKEN_EXPIRED;


public class JwtExpiredTokenException extends JwtAuthenticationException{
    @Serial
    private static final long serialVersionUID = -7285211528095468156L;

    public JwtExpiredTokenException() {
        super(TOKEN_EXPIRED);
    }

    public JwtExpiredTokenException(String token) {
        super(token, TOKEN_EXPIRED);
    }

    public JwtExpiredTokenException(String token, Throwable cause) {
        super(token, TOKEN_EXPIRED, cause);
    }
}
