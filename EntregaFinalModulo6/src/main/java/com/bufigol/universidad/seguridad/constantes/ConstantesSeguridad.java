package com.bufigol.universidad.seguridad.constantes;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Clase de constantes para la configuración y manejo de seguridad
 * Incluye constantes para JWT, roles, patrones de validación, configuración de seguridad y CORS
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConstantesSeguridad {

    // ========================================
    // Constantes relacionadas con JWT
    // ========================================
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_ISSUER = "universidad-api";
    public static final String TOKEN_AUDIENCE = "universidad-clients";
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_SUBJECT = "user-details";

    // Mensajes de error JWT
    public static final String TOKEN_MISSING = "Token JWT no proporcionado";
    public static final String TOKEN_EXPIRED = "Token JWT expirado";
    public static final String TOKEN_INVALID = "Token JWT inválido";
    public static final String TOKEN_UNSUPPORTED = "Token JWT no soportado";
    public static final String TOKEN_CLAIMS_EMPTY = "Claims del JWT vacíos";

    // JWT Claims
    public static final String CLAIM_ROLES = "roles";
    public static final String CLAIM_USERNAME = "sub";
    public static final String CLAIM_CREATED = "created";
    public static final String CLAIM_USER_ID = "userId";

    // ========================================
    // Constantes de Roles y Autorización
    // ========================================
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_CLIENT = "ROLE_CLIENT";
    public static final String ROLE_PREFIX = "ROLE_";

    // Mensajes de autenticación
    public static final String AUTH_FAILED = "Error de autenticación: ";
    public static final String ACCESS_DENIED = "Acceso denegado: ";
    public static final String USER_DISABLED = "Usuario deshabilitado";
    public static final String INVALID_CREDENTIALS = "Credenciales inválidas";

    // ========================================
    // Patrones de Validación
    // ========================================
    public static final String NAME_PATTERN = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$";
    public static final String USERNAME_PATTERN = "^[a-zA-Z0-9._-]{3,}$";
    public static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
    public static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>])(?=\\S+$).{6,}$";

    public static final String PASSWORD_MESSAGE =
            "La contraseña debe contener al menos un número, una letra mayúscula, " +
                    "una letra minúscula, un carácter especial y no debe contener espacios";

    // ========================================
    // Límites y Restricciones
    // ========================================
    public static final int MIN_USERNAME_LENGTH = 3;
    public static final int MAX_USERNAME_LENGTH = 50;
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MAX_PASSWORD_LENGTH = 100;
    public static final int MAX_EMAIL_LENGTH = 100;
    public static final int MIN_NAME_LENGTH = 3;
    public static final int MAX_NAME_LENGTH = 100;
    public static final int MAX_ROLES_PER_USER = 2;

    // Configuración de sesión y bloqueo
    public static final long MAX_SESSION_IDLE_TIME = 30; // minutos
    public static final int MAX_LOGIN_ATTEMPTS = 5;
    public static final int ACCOUNT_LOCK_TIME = 300; // segundos
    public static final long TOKEN_EXPIRATION_TIME = 86400000; // 24 horas en milisegundos

    // ========================================
    // URLs y Endpoints
    // ========================================
    public static final String API_ROOT_PATH = "/api/**";
    public static final String ADMIN_ROOT_PATH = "/api/admin/**";
    public static final String PUBLIC_ROOT_PATH = "/api/public/**";
    public static final String SIGN_UP_URL = "/api/auth/signup";
    public static final String SIGN_IN_URL = "/api/auth/logIn";

    // URLs públicas permitidas
    public static final String[] PUBLIC_URLS = {
            "/api/auth/**",
            "/api/public/**",
            "/",
            "/login",
            "/registro",
            "/error",
            "/css/**",
            "/js/**",
            "/images/**",
            "/webjars/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/actuator/health"
    };
    // ========================================
    // Configuración CORS
    // ========================================
    public static final String[] ALLOWED_ORIGINS = {"*"};
    public static final String[] ALLOWED_METHODS = {
            "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"
    };
    public static final String[] ALLOWED_HEADERS = {
            "Authorization",
            "Content-Type",
            "Accept",
            "X-Requested-With",
            "Cache-Control",
            "access-control-allow-origin",
            "access-control-allow-credentials",
            "access-control-allow-headers"
    };
    public static final String[] EXPOSED_HEADERS = {
            "Authorization",
            "X-Custom-header"
    };
    public static final long MAX_AGE = 3600L; // 1 hora

    // ========================================
    // Configuración de Seguridad
    // ========================================
    public static final String[] SWAGGER_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    public static final String[] ACTUATOR_WHITELIST = {
            "/actuator/health",
            "/actuator/info"
    };

    public static final String[] STATIC_RESOURCES = {
            "/css/**",
            "/js/**",
            "/img/**",
            "/webjars/**"
    };

    // ========================================
    // Códigos de Error
    // ========================================
    public static final String ERROR_PREFIX = "SEC_ERR_";
    public static final String AUTH_ERROR = ERROR_PREFIX + "AUTH_";
    public static final String TOKEN_ERROR = ERROR_PREFIX + "TOKEN_";
    public static final String VALIDATION_ERROR = ERROR_PREFIX + "VAL_";

    public static final String ERR_INVALID_TOKEN = TOKEN_ERROR + "001";
    public static final String ERR_EXPIRED_TOKEN = TOKEN_ERROR + "002";
    public static final String ERR_UNSUPPORTED_TOKEN = TOKEN_ERROR + "003";
    public static final String ERR_INVALID_CREDENTIALS = AUTH_ERROR + "001";
    public static final String ERR_ACCOUNT_DISABLED = AUTH_ERROR + "002";
    public static final String ERR_ACCOUNT_LOCKED = AUTH_ERROR + "003";
}