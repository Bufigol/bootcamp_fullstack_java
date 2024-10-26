package com.bufigol.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Comprobadores {
    // Método para validar el formato del correo electrónico
    public static boolean esEmailValido(String email) {
        String patron = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Método para comprobar si el dominio existe
    public static boolean dominioExiste(String email) {
        String dominio = email.substring(email.indexOf('@') + 1);
        try {
            return InetAddress.getByName(dominio).isReachable(1000); // El dominio existe
        } catch (UnknownHostException e) {
            return false; // El dominio no existe
        } catch (IOException e) {
            return false;
        }
    }

    // Método para comprobar el correo electrónico
    public static boolean comprobarEmail(String email) {
        if (esEmailValido(email)) {
            if (dominioExiste(email)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
