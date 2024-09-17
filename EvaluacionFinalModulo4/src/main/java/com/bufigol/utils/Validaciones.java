package com.bufigol.utils;

import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validaciones {
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            return false;
        }

        String domain = email.split("@")[1];
        try {
            InetAddress address = InetAddress.getByName(domain);
            return address.getHostAddress() != null;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidRut(String rut) {
        // Eliminar puntos y guión del RUT
        rut = rut.replace(".", "").replace("-", "");

        // Verificar longitud mínima y máxima
        if (rut.length() < 2 || rut.length() > 9) {
            return false;
        }
        try {
            String rutDigits = rut.substring(0, rut.length() - 1);
            char dv = rut.charAt(rut.length() - 1);

            int rutNumber = Integer.parseInt(rutDigits);

            return calculateDv(rutNumber) == dv;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static char calculateDv(int rut) {
        int m = 0, s = 1;
        for (; rut != 0; rut /= 10) {
            s = (s + rut % 10 * (9 - m++ % 6)) % 11;
        }
        return (char) (s != 0 ? s + 47 : 75);
    }
}
