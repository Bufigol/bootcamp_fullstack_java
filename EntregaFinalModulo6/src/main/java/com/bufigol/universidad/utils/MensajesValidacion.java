package com.bufigol.universidad.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MensajesValidacion {

    // Mensajes para validación de nombre
    public static final String NOMBRE_REQUERIDO = "El nombre no puede estar vacío";
    public static final String NOMBRE_LONGITUD = "El nombre debe tener entre 3 y 100 caracteres";
    public static final String NOMBRE_FORMATO = "El nombre solo puede contener letras y espacios";

    // Mensajes para validación de username
    public static final String USERNAME_REQUERIDO = "El nombre de usuario no puede estar vacío";
    public static final String USERNAME_LONGITUD = "El nombre de usuario debe tener entre 3 y 50 caracteres";
    public static final String USERNAME_FORMATO = "El nombre de usuario solo puede contener letras, números, puntos, guiones bajos y guiones medios";

    // Mensajes para validación de email
    public static final String EMAIL_REQUERIDO = "El correo electrónico no puede estar vacío";
    public static final String EMAIL_FORMATO = "Debe proporcionar un correo electrónico válido";
    public static final String EMAIL_LONGITUD = "El correo electrónico no puede exceder los 100 caracteres";

    // Mensajes para validación de contraseña
    public static final String PASSWORD_REQUERIDO = "La contraseña no puede estar vacía";
    public static final String PASSWORD_LONGITUD = "La contraseña debe tener entre 6 y 100 caracteres";
    public static final String PASSWORD_FORMATO = "La contraseña debe contener al menos un número, una letra mayúscula, una letra minúscula, un carácter especial y no debe contener espacios";
    public static final String CONFIRM_PASSWORD_REQUERIDO = "La confirmación de contraseña no puede estar vacía";
    public static final String PASSWORDS_NO_COINCIDEN = "Las contraseñas no coinciden";

    // Mensajes para validación de roles
    public static final String ROLES_REQUERIDOS = "Debe seleccionar al menos un rol";
    public static final String ROLES_INVALIDOS = "Uno o más roles seleccionados no son válidos";
    public static final String ROLES_MAXIMOS_EXCEDIDOS = "Se ha excedido el número máximo de roles permitidos";

    // Mensajes generales de registro
    public static final String REGISTRO_EXITOSO = "Usuario registrado exitosamente";
    public static final String ERROR_REGISTRO = "Error durante el registro del usuario";
    public static final String USERNAME_DUPLICADO = "El nombre de usuario ya existe";
    public static final String EMAIL_DUPLICADO = "El correo electrónico ya está registrado";
}