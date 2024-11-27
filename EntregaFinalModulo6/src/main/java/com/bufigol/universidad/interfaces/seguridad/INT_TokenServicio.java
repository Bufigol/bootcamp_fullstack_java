package com.bufigol.universidad.interfaces.seguridad;

import org.springframework.security.core.Authentication;
import java.util.Date;
import java.util.List;

public interface INT_TokenServicio {
    /**
     * Genera un nuevo token JWT para el usuario especificado
     *
     * @param username nombre de usuario
     * @param roles roles del usuario
     * @return token JWT generado
     */
    String generateToken(String username, List<String> roles);

    /**
     * Valida si un token JWT es válido
     *
     * @param token token JWT a validar
     * @return true si el token es válido, false en caso contrario
     */
    boolean validateToken(String token);

    /**
     * Obtiene el nombre de usuario del token JWT
     *
     * @param token token JWT
     * @return nombre de usuario extraído del token
     */
    String getUsernameFromToken(String token);

    /**
     * Obtiene la autenticación a partir del token JWT
     *
     * @param token token JWT
     * @return objeto Authentication con los detalles del usuario
     */
    Authentication getAuthentication(String token);

    /**
     * Obtiene la fecha de expiración del token JWT
     *
     * @param token token JWT
     * @return Date con la fecha de expiración del token
     */
    Date getExpirationDateFromToken(String token);
}