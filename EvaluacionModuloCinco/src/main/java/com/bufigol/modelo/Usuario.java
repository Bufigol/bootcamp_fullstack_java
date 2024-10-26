package com.bufigol.modelo;

import com.bufigol.utils.Comprobadores;
import com.bufigol.utils.PasswordGenerator;

import java.sql.Date;
import java.util.Objects;

import static com.bufigol.utils.Comprobadores.*;

public class Usuario {

    private long id;
    private String nombre;
    private String userName;
    private String email;
    private Date fechaNacimiento;
    private String password;
    private Horoscopo horoscopo;

    public Usuario(long id, String nombre, String userName, String email, Date fechaNacimiento, String password, Horoscopo horoscopo) {
        this.id = id;
        this.nombre = nombre;
        this.userName = userName;
        setEmail(email);
        this.fechaNacimiento = fechaNacimiento;
        setPassword(password);
        this.horoscopo = horoscopo;
    }

    public Usuario() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(Comprobadores.comprobarEmail(email))
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
        this.password = PasswordGenerator.generarContrasenaSegura(password);
    }

    public Horoscopo getHoroscopo() {
        return horoscopo;
    }

    public void setHoroscopo(Horoscopo horoscopo) {
        this.horoscopo = horoscopo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario usuario)) return false;
        return getId() == usuario.getId() && Objects.equals(getNombre(), usuario.getNombre()) && Objects.equals(getUserName(), usuario.getUserName()) && Objects.equals(getEmail(), usuario.getEmail()) && Objects.equals(getFechaNacimiento(), usuario.getFechaNacimiento()) && Objects.equals(getPassword(), usuario.getPassword()) && Objects.equals(getHoroscopo(), usuario.getHoroscopo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNombre(), getUserName(), getEmail(), getFechaNacimiento(), getPassword(), getHoroscopo());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Usuario{");
        sb.append("id=").append(id);
        sb.append(", nombre='").append(nombre).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", fechaNacimiento=").append(fechaNacimiento);
        sb.append(", password='").append(password).append('\'');
        sb.append(", horoscopo=").append(horoscopo);
        sb.append('}');
        return sb.toString();
    }


}
