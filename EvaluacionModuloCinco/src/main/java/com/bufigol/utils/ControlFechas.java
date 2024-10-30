package com.bufigol.utils;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ControlFechas {

    /**
     * Convierte una cadena de texto en una fecha.
     * Si la cadena no puede ser analizada, se devuelve la fecha actual.
     *
     * @param fechaNacimientoStr la cadena que representa la fecha en formato "yyyy-MM-dd"
     * @return la fecha convertida o la fecha actual si la conversión falla
     */
    public static Date dateFromString(String fechaNacimientoStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate localDate = LocalDate.parse(fechaNacimientoStr, formatter);
            return Date.valueOf(localDate);
        } catch (DateTimeParseException | NullPointerException e) {
            // En caso de error, devolvemos la fecha actual
            return Date.valueOf(LocalDate.now());
        } catch (Exception e) {
            // Captura cualquier otra excepción inesperada y devuelve la fecha actual
            return Date.valueOf(LocalDate.now());
        }
    }
}
