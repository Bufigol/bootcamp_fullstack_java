package com.bufigol.evm6s4.interfaces;

import com.bufigol.evm6s4.modelo.Usuario;

import java.util.List;

public interface INT_UsuarioServicio {
    boolean agregarUsuario(Usuario usuario);
    boolean editarUsuario(Usuario usuario);
    boolean eliminarUsuario(String username);
    List<Usuario> listarUsuarios();
    Usuario buscarPorUsername(String username);
    boolean validarDatos(Usuario usuario);
    boolean validarUsername(String username);
    boolean validarNombre(String nombre);
    boolean validarApellido(String apellido);
    boolean validarPassword(String password);
    boolean validarEmail(String email);
}
