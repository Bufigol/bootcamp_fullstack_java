package com.bufigol.utils;

import com.bufigol.interfaces.InetAddressWrapper;

import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for various validations.
 */
public class Validaciones {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private final InetAddressWrapper inetAddressWrapper;

    /**
     * Constructs a new Validaciones instance with the specified InetAddressWrapper.
     *
     * @param inetAddressWrapper The InetAddressWrapper to use for DNS lookups.
     */
    public Validaciones(InetAddressWrapper inetAddressWrapper) {
        this.inetAddressWrapper = inetAddressWrapper;
    }

    /**
     * Validates an email address.
     * This method checks both the format of the email and the existence of the domain.
     *
     * @param email The email address to validate.
     * @return true if the email is valid, false otherwise.
     */
    public boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }

        // Validate email format
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            return false;
        }

        // Validate domain
        String domain = email.substring(email.lastIndexOf("@") + 1);
        try {
            return inetAddressWrapper.getAllByName(domain).length > 0;
        } catch (UnknownHostException e) {
            return false;
        }
    }

    /**
     * Validates a Chilean RUT (Rol Único Tributario).
     *
     * @param rut The RUT to validate.
     * @return true if the RUT is valid, false otherwise.
     */
    public static boolean isValidRut(String rut) {
        if (rut == null || rut.isEmpty()) {
            return false;
        }

        // Remove dots and hyphen
        rut = rut.replace(".", "").replace("-", "");

        // Check length
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

    /**
     * Calculates the verification digit for a given RUT number.
     *
     * @param rut The RUT number without the verification digit.
     * @return The calculated verification digit.
     */
    private static char calculateDv(int rut) {
        int m = 0, s = 1;
        for (; rut != 0; rut /= 10) {
            s = (s + rut % 10 * (9 - m++ % 6)) % 11;
        }
        return (char) (s != 0 ? s + 47 : 75);
    }
}