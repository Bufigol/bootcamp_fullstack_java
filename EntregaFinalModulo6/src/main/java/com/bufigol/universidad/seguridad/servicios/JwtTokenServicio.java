package com.bufigol.universidad.seguridad.servicios;

import com.bufigol.universidad.excepciones.seguridad.JwtAuthenticationException;
import com.bufigol.universidad.interfaces.seguridad.INT_TokenServicio;
import com.bufigol.universidad.seguridad.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenServicio implements INT_TokenServicio {

    private final JwtTokenProvider tokenProvider;

    @Override
    public String generateToken(String username, List<String> roles) {
        log.debug("Generando token para usuario: {}", username);
        return tokenProvider.createToken(username, roles);
    }

    @Override
    public boolean validateToken(String token) {
        try {
            return tokenProvider.validateToken(token);
        } catch (Exception e) {
            log.error("Error validando token: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public String getUsernameFromToken(String token) {
        return tokenProvider.getUsername(token);
    }

    @Override
    public Authentication getAuthentication(String token) {
        return tokenProvider.getAuthentication(token);
    }

    @Override
    public Date getExpirationDateFromToken(String token) {
        log.debug("Obteniendo fecha de expiración para token");
        try {
            return tokenProvider.getExpirationDateFromToken(token);
        } catch (Exception e) {
            log.error("Error al obtener fecha de expiración del token: {}", e.getMessage());
            throw new JwtAuthenticationException("Error al procesar la fecha de expiración del token", e.getMessage());
        }
    }
}
