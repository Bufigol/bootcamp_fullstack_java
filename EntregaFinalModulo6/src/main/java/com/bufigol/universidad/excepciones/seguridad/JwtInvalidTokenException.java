package com.bufigol.universidad.excepciones.seguridad;

import java.io.Serial;

import static com.bufigol.universidad.seguridad.constantes.ConstantesSeguridad.TOKEN_INVALID;

public class JwtInvalidTokenException  extends JwtAuthenticationException{
    @Serial
    private static final long serialVersionUID = -5959543783324224864L;

    public JwtInvalidTokenException() {
        super(TOKEN_INVALID);
    }

    public JwtInvalidTokenException(String token) {
        super(token, TOKEN_INVALID);
    }

    public JwtInvalidTokenException(String token, String message) {
        super(token, message);
    }

    public JwtInvalidTokenException(String token, String message, Throwable cause) {
        super(token, message, cause);
    }
}
