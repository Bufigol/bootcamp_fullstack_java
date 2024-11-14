package com.bufigol.universidad.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtils {
    public static String generarPasswordSegura(String input) {
        try {
            // Generar una sal aleatoria
            byte[] salt = new byte[16];
            SecureRandom random = new SecureRandom();
            random.nextBytes(salt);

            // Crear el hash de la contraseña utilizando SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(input.getBytes());

            // Combinar la sal y la contraseña hasheada
            byte[] saltAndHash = new byte[salt.length + hashedPassword.length];
            System.arraycopy(salt, 0, saltAndHash, 0, salt.length);
            System.arraycopy(hashedPassword, 0, saltAndHash, salt.length, hashedPassword.length);

            // Devolver la contraseña segura en base64
            return Base64.getEncoder().encodeToString(saltAndHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al crear la contraseña segura", e);
        }
    }


    public static boolean comprobarPassWord(String userInput, String storedPassword) {
        try {
            // Decodificar la contraseña almacenada
            byte[] saltAndHash = Base64.getDecoder().decode(storedPassword);
            // Separar la sal y el hash
            byte[] salt = new byte[16];
            byte[] storedHash = new byte[saltAndHash.length - 16];
            System.arraycopy(saltAndHash, 0, salt, 0, salt.length);
            System.arraycopy(saltAndHash, salt.length, storedHash, 0, storedHash.length);

            // Crear el hash de la entrada del usuario con la misma sal
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] userHash = md.digest(userInput.getBytes());

            // Comparar el hash del usuario con el hash almacenado
            return MessageDigest.isEqual(userHash, storedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al verificar la contraseña", e);
        }
    }
}
