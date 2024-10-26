package com.bufigol.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordGenerator {
    // Método para generar una contraseña segura a partir de un String
    public static String generarContrasenaSegura(String input) {
        // Generar un "sal" aleatorio
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);

        // Concatenar el input con el "sal"
        String inputConSal = input + Base64.getEncoder().encodeToString(salt);

        // Hashear la contraseña
        String hashedPassword = hashPassword(inputConSal);

        // Devolver el "sal" y la contraseña hasheada
        return Base64.getEncoder().encodeToString(salt) + ":" + hashedPassword;
    }

    // Método para hashear la contraseña usando SHA-256
    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al hashear la contraseña", e);
        }
    }

    // Método para verificar si la contraseña ingresada coincide con la almacenada
    public static boolean verificarContrasena(String input, String storedPassword) {
        // Extraer el "sal" del storedPassword
        String[] parts = storedPassword.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Formato de contraseña almacenada inválido.");
        }

        // Obtener el "sal" y la contraseña hasheada almacenada
        String storedSalt = parts[0];
        String storedHash = parts[1];

        // Concatenar el input con el "sal" y hashearlo
        String inputConSal = input + storedSalt;
        String hashedInput = hashPassword(inputConSal);

        // Comparar el hash generado con el hash almacenado
        return hashedInput.equals(storedHash);
    }

}
