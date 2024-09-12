package com.bufigol.utils;

public class ComprobacionesVarias {
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
