package com.bufigol.ecm6s4.interfaces;

import com.bufigol.ecm6s4.modelo.Usuario;

public interface INT_UsuarioService {

    boolean validarNombre(Usuario usuario);
    boolean validarApellido(Usuario usuario);
    boolean validarNombreYApellido(Usuario usuario);
    boolean validarUserName(Usuario usuario);
    boolean validarEmail(Usuario usuario);
    boolean validarPassword(Usuario usuario);
    boolean validarUsuario(Usuario usuario);
    boolean registrarUsuario(Usuario usuario);
}
