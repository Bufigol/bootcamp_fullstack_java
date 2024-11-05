package com.bufigol.modelo;

public class Usuario {
    private String nombreUsuario;
    private String pwd;

    public Usuario(String nombreUsuario, String pwd) {
        this.nombreUsuario = nombreUsuario;
        this.pwd = pwd;
    }

    public Usuario() {
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;
        return getNombreUsuario().equals(usuario.getNombreUsuario()) && getPwd().equals(usuario.getPwd());
    }

    @Override
    public int hashCode() {
        int result = getNombreUsuario().hashCode();
        result = 31 * result + getPwd().hashCode();
        return result;
    }
}
