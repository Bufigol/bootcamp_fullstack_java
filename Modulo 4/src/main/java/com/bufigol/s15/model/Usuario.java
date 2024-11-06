package com.bufigol.s15.model;

import com.bufigol.utils.ComprobacionesVarias;
import com.bufigol.utils.EntradaPorTeclado;

import java.util.StringJoiner;

public class Usuario {
    private String nombre;
    private String apellido;
    private String correo;
    private String password;
    private String ciudad;
    private String region;
    private String pais;
    private String continente;

    public Usuario(String nombre, String apellido, String correo, String password, String ciudad, String region, String pais, String continente) {
        this.nombre = nombre;
        this.apellido = apellido;
        setCorreo(correo);
        this.password = EntradaPorTeclado.generateSecurePassword(password);
        this.ciudad = ciudad;
        this.region = region;
        this.pais = pais;
        this.continente = continente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        if(ComprobacionesVarias.isValidEmail(correo)){
            this.correo = correo;
        }else{
            this.correo = "";
        }

    }

    public void setPassword(String password) {
        this.password = EntradaPorTeclado.generateSecurePassword(password);
    }

    public String getPassword() {
        return password;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getContinente() {
        return continente;
    }

    public void setContinente(String continente) {
        this.continente = continente;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Usuario.class.getSimpleName() + "[", "]")
                .add("nombre='" + nombre + "'")
                .add("apellido='" + apellido + "'")
                .add("correo='" + correo + "'")
                .add("ciudad='" + ciudad + "'")
                .add("region='" + region + "'")
                .add("pais='" + pais + "'")
                .add("continente='" + continente + "'")
                .toString();
    }


}
