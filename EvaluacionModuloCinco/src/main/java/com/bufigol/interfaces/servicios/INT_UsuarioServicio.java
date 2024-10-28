package com.bufigol.interfaces.servicios;

import com.bufigol.dtos.usuario.UsuarioCreateDto;
import com.bufigol.dtos.usuario.UsuarioResponseDTO;
import com.bufigol.dtos.usuario.UsuarioUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface INT_UsuarioServicio {
    List<UsuarioResponseDTO> listarUsuarios();
    Optional<UsuarioResponseDTO> buscarUsuarioPorId(int id);
    void crearUsuario(UsuarioCreateDto usuarioCreateDTO);
    void actualizarUsuario(UsuarioUpdateDTO usuarioUpdateDTO);
    void eliminarUsuario(int id);
}
