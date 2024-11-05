package com.bufigol.dtos.usuario;

import com.bufigol.modelo.Horoscopo;

import java.sql.Date;

public class UsuarioUpdateDTO {
    private int id;
    private String nombre;
    private String username;
    private String email;
    private Date fechaNacimiento;
    private String password;
    private Horoscopo horoscopo;

    public UsuarioUpdateDTO(int id, String nombre, String username, String email, Date fechaNacimiento, String password, Horoscopo horoscopo) {
        this.id = id;
        this.nombre = nombre;
        this.username = username;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.password = password;
        this.horoscopo = horoscopo;
    }

    public UsuarioUpdateDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Horoscopo getHoroscopo() {
        return horoscopo;
    }

    public void setHoroscopo(Horoscopo horoscopo) {
        this.horoscopo = horoscopo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UsuarioUpdateDTO{");
        sb.append("id=").append(id);
        sb.append(", nombre='").append(nombre).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", fechaNacimiento=").append(fechaNacimiento);
        sb.append(", password='").append(password).append('\'');
        sb.append(", horoscopoId=").append(horoscopo);
        sb.append('}');
        return sb.toString();
    }
}
