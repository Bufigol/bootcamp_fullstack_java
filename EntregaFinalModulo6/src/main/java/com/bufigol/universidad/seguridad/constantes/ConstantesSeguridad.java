package com.bufigol.universidad.seguridad.constantes;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConstantesSeguridad {

    // JWT related constants
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/auth/signup";
    public static final String SIGN_IN_URL = "/api/auth/signin";

    // Role related constants
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_PREFIX = "ROLE_";

    // Security paths
    public static final String API_ROOT_PATH = "/api/**";
    public static final String ADMIN_ROOT_PATH = "/api/admin/**";
    public static final String PUBLIC_ROOT_PATH = "/api/public/**";

    // Error messages
    public static final String TOKEN_MISSING = "Token JWT no proporcionado";
    public static final String TOKEN_EXPIRED = "Token JWT expirado";
    public static final String TOKEN_INVALID = "Token JWT inválido";
    public static final String TOKEN_UNSUPPORTED = "Token JWT no soportado";
    public static final String TOKEN_CLAIMS_EMPTY = "Claims del JWT vacíos";

    // Authentication messages
    public static final String AUTH_FAILED = "Error de autenticación: ";
    public static final String ACCESS_DENIED = "Acceso denegado: ";
    public static final String USER_DISABLED = "Usuario deshabilitado";
    public static final String INVALID_CREDENTIALS = "Credenciales inválidas";

    // JWT Claims
    public static final String CLAIM_ROLES = "roles";
    public static final String CLAIM_USERNAME = "sub";

    // Security Configuration
    public static final long MAX_SESSION_IDLE_TIME = 30; // minutes
    public static final int MAX_LOGIN_ATTEMPTS = 5;
    public static final int ACCOUNT_LOCK_TIME = 300; // seconds
    public static final String[] PUBLIC_URLS = {
            "/api/auth/**",
            "/api/public/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/actuator/health"
    };

    // CORS Configuration
    public static final String[] ALLOWED_ORIGINS = {"*"};
    public static final String[] ALLOWED_METHODS = {
            "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"
    };
    public static final String[] ALLOWED_HEADERS = {
            "Authorization",
            "Content-Type",
            "Accept",
            "X-Requested-With",
            "remember-me"
    };
    public static final String[] EXPOSED_HEADERS = {
            "Authorization",
            "X-Custom-header"
    };
    public static final long MAX_AGE = 3600L;

    // Password validation
    public static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    public static final String PASSWORD_MESSAGE =
            "La contraseña debe contener al menos 8 caracteres, incluyendo mayúsculas, " +
                    "minúsculas, números y caracteres especiales";


}
