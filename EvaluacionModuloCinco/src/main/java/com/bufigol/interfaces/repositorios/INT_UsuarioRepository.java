package com.bufigol.interfaces.repositorios;

import com.bufigol.modelo.Usuario;

import java.util.List;
import java.util.Optional;

public interface INT_UsuarioRepository {
    List<Usuario> listarUsuarios();
    void insertarUsuario(Usuario usuario);
    Optional<Usuario> buscarUsuario(int id);
    void actualizarUsuario(Usuario usuario);
    void eliminarUsuario(int id);
}
