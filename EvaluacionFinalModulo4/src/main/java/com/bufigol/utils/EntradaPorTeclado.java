package com.bufigol.utils;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class EntradaPorTeclado {
    private static Scanner scanner = null;
    private static final String HASH_ALGORITHM = "SHA-256";
    private static final int SALT_SIZE = 16;

    public static int pedirEntero(String msg, Scanner sc) {
        System.out.println(msg);
        while (true) {
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número entero.");
                sc.nextLine(); // Limpiar el buffer
            }
        }
    }
    public static int pedirEntero(String s) {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        return pedirEntero(s, scanner);
    }

    public static String pedirCadena(String msg) {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        System.out.println(msg);
        return scanner.nextLine();
    }

    public static String generateSecurePassword(String password) {
        byte[] salt = new byte[SALT_SIZE];
        new SecureRandom().nextBytes(salt);
        String saltedPassword = password + Base64.getEncoder().encodeToString(salt);
        try {
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] hashedPassword = md.digest(saltedPassword.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword) + "." + Base64.getEncoder().encodeToString(salt);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public static boolean verifyPassword(String inputPassword, String storedPassword) {
        String[] parts = storedPassword.split("\\.");
        if (parts.length != 2) return false;
        String storedHash = parts[0];
        String storedSalt = parts[1];
        String saltedInputPassword = inputPassword + storedSalt;
        try {
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] hashedInputPassword = md.digest(saltedInputPassword.getBytes());
            String inputHash = Base64.getEncoder().encodeToString(hashedInputPassword);
            return storedHash.equals(inputHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error verifying password", e);
        }
    }


}