package com.bufigol.utils;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class EntradaPorTeclado {
    private static Scanner scanner = null;
    private static final String HASH_ALGORITHM = "SHA-256";
    private static final int SALT_SIZE = 16; // 128 bits

    public static int pedirEntero(String msg) {
        if (scanner == null) {
            scanner = new Scanner(System.in); // Open scanner only if it's null
        }
        System.out.println(msg);
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número entero.");
                scanner.nextLine(); // Limpiar el buffer
            }
        }
    }

    public static String pedirCadena(String msg) {
        if (scanner == null) {
            scanner = new Scanner(System.in); // Open scanner only if it's null
            scanner.nextLine(); // Limpiar el buffer
        }
        System.out.println(msg);
        String out=scanner.nextLine();
        if(out.isEmpty()){
            return pedirCadena(msg);
        }else{
            return out;
        }

    }

    public static void cerrarScanner() {
        if (scanner != null) {
            scanner.close();
            scanner = null;
        }
    }

    public static String generateSecurePassword(String password) {
        // Generate a random salt
        byte[] salt = new byte[SALT_SIZE];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);

        // Hash the password with the salt
        String saltedPassword = password + new String(Base64.getEncoder().encode(salt));
        byte[] hashedPassword = hash(saltedPassword);

        // Return the hashed password with the salt
        return new String(Base64.getEncoder().encode(hashedPassword)) + "." + new String(Base64.getEncoder().encode(salt));
    }

    public static boolean verifyPassword(String inputPassword, String storedPassword) {
        // Extract the salt from the stored password
        String[] parts = storedPassword.split("\\.");
        if (parts.length != 2) {
            throw new RuntimeException("Invalid stored password format");
        }
        byte[] salt = Base64.getDecoder().decode(parts[1]);

        // Hash the input password with the salt
        String saltedInputPassword = inputPassword + new String(salt);
        byte[] hashedInputPassword = hash(saltedInputPassword);

        // Compare the hashed input password with the stored password
        byte[] storedHash = Base64.getDecoder().decode(parts[0]);
        return Arrays.equals(hashedInputPassword, storedHash);
    }

    private static byte[] hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            return digest.digest(input.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}