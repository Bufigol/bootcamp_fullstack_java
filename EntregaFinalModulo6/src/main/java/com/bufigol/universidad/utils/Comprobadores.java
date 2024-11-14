package com.bufigol.universidad.utils;

public class Comprobadores {
    public static boolean isValidRUT(String rut) {
        // Normalizar el RUT: eliminar puntos y guiones
        String normalizedRUT = rut.replace(".", "").replace("-", "").toUpperCase();

        // Verificar que el RUT tenga al menos 2 caracteres (número + dígito verificador)
        if (normalizedRUT.length() < 2) {
            return false;
        }

        // Extraer el cuerpo y el dígito verificador
        String body = normalizedRUT.substring(0, normalizedRUT.length() - 1);
        char dv = normalizedRUT.charAt(normalizedRUT.length() - 1);

        // Validar que el cuerpo contenga solo dígitos
        if (!body.matches("\\d+")) {
            return false;
        }

        // Calcular el dígito verificador
        int rutNumber = Integer.parseInt(body);
        int calculatedDV = calculateDV(rutNumber);

        // Comparar el dígito verificador calculado con el proporcionado
        return dv == (calculatedDV == 10 ? 'K' : Character.forDigit(calculatedDV, 10));
    }

    private static int calculateDV(int rut) {
        int sum = 0;
        int multiplier = 2;

        while (rut > 0) {
            sum += (rut % 10) * multiplier;
            rut /= 10;
            multiplier = (multiplier == 7) ? 2 : multiplier + 1;
        }

        int remainder = sum % 11;
        return 11 - remainder;
    }
}
