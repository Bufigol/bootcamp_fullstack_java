package com.bufigol.universidad.seguridad.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.Duration;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
@ConfigurationProperties(prefix = "jwt")
@Validated
@Getter
@Setter
public class JwtProperties {

    /**
     * Secret key para firmar los tokens JWT
     */
    @NotBlank(message = "La secret key no puede estar vacía")
    private String secret;

    /**
     * Tiempo de expiración del token en milisegundos
     */
    @NotNull(message = "El tiempo de expiración no puede ser nulo")
    @Positive(message = "El tiempo de expiración debe ser positivo")
    private Long expiration;

    /**
     * Tiempo de expiración del refresh token en milisegundos
     */
    @NotNull(message = "El tiempo de expiración del refresh token no puede ser nulo")
    @Positive(message = "El tiempo de expiración del refresh token debe ser positivo")
    private Long refreshExpiration;

    /**
     * Nombre del header donde se enviará el token
     */
    @NotBlank(message = "El nombre del header no puede estar vacío")
    private String header = "Authorization";

    /**
     * Prefijo del token en el header
     */
    @NotBlank(message = "El prefijo del token no puede estar vacío")
    private String prefix = "Bearer ";

    /**
     * Issuer del token
     */
    private String issuer = "universidad-api";

    /**
     * Audience del token
     */
    private String audience = "universidad-clients";

    /**
     * Obtener el tiempo de expiración como Duration
     */
    public Duration getExpirationDuration() {
        return Duration.ofMillis(expiration);
    }

    /**
     * Obtener el tiempo de expiración del refresh token como Duration
     */
    public Duration getRefreshExpirationDuration() {
        return Duration.ofMillis(refreshExpiration);
    }

    /**
     * Claims personalizados
     */
    @Getter
    @Setter
    public static class Claims {
        private String roles = "roles";
        private String username = "sub";
    }

    private Claims claims = new Claims();

    /**
     * Obtener la clave secreta como un objeto Key
     */
    public Key getSecretKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secret.getBytes());
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }
}