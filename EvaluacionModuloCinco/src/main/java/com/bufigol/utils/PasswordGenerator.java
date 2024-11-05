package com.bufigol.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordGenerator {
    // Método para generar una contraseña segura
    public static String generarContrasenaSegura(String input) {
        try {
            // Crea un objeto MessageDigest para SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Aplica el hash a la entrada
            byte[] hash = digest.digest(input.getBytes());
            // Convierte el hash a una representación hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            // Devuelve la contraseña segura
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar la contraseña segura", e);
        }
    }

    // Método para verificar una contraseña
    public static boolean verificarContrasena(String input, String pwdGuardada) {
        // Genera el hash de la entrada
        String hashInput = generarContrasenaSegura(input);
        // Compara el hash de la entrada con la contraseña guardada
        return hashInput.equals(pwdGuardada);
    }
}
