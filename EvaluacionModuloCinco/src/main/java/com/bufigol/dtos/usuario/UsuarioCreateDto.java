package com.bufigol.dtos.usuario;

import com.bufigol.constantes.ConstantesTablas;
import com.bufigol.modelo.Horoscopo;
import com.bufigol.modelo.Usuario;
import com.bufigol.repositorio.HoroscopoRepository;
import com.bufigol.utils.UtilidadesDDBB;

import java.sql.Date;

public class UsuarioCreateDto {
    private int id;
    private String nombre;
    private String username;
    private String email;
    private Date fechaNacimiento;
    private String password;
    private Horoscopo horoscopo;

    public UsuarioCreateDto(int id, String nombre, String username, String email, Date fechaNacimiento, String password, Horoscopo horoscopo) {
        this.id = id;
        this.nombre = nombre;
        this.username = username;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.password = password;
        this.horoscopo = horoscopo;
    }

    public UsuarioCreateDto(Usuario usr){
        this.id = UtilidadesDDBB.buscarIDDisponible(ConstantesTablas.USUARIOS_TABLE,ConstantesTablas.USUARIOS_TABLE_COLUMNS[0]);
        this.nombre = usr.getNombre();
        this.username = usr.getUserName();
        this.email = usr.getEmail();
        this.fechaNacimiento = usr.getFechaNacimiento();
        this.password = usr.getPassword();
        HoroscopoRepository hr= new HoroscopoRepository();
        this.horoscopo = hr.buscarHoroscopoPorFecha(usr.getFechaNacimiento());
    }

    public UsuarioCreateDto() {
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
}
