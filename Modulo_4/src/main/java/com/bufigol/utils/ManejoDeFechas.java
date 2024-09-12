package com.bufigol.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ManejoDeFechas {
    public static Date stringToDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        formatter.setLenient(false);  // Esto hace que el parsing sea más estricto

        try {
            return formatter.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Error al parsear la fecha: " + e.getMessage());
            return null;
        }
    }

    public static String dateToString(Date date) {
        if (date == null) {
            return null;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
    }
}
