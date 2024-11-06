package com.bufigol.s6;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Persona {
    private String nombre;
    private String usrName;
    private String pwd;

    public Persona(String nombre, String pwd) {
        this.nombre = nombre;
        this.pwd = convertToPassword(pwd);
    }

    public Persona(String nombre, String pwd, String usrName) {
        this.pwd = convertToPassword(pwd);
        this.usrName = usrName;
        this.nombre = nombre;
    }

    public Persona() {
    }


    private  String convertToPassword(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(text.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean verifyPassword(String inputPassword, String storedPassword) {
        String hashedInput = convertToPassword(inputPassword);
        return hashedInput != null && hashedInput.equals(storedPassword);
    }

    public boolean verifyPassword(String inputPassword) {
        return verifyPassword(inputPassword, this.pwd);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }
}
