package com.bufigol.evm6s4.interfaces;

import com.bufigol.evm6s4.modelo.Usuario;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

public interface INT_UsuarioController {

    String listarUsuarios(Model model);
    String mostrarFormularioAgregar(Model model);
    String agregarUsuario(@ModelAttribute Usuario usuario);
    String mostrarFormularioEditar(@PathVariable String username, Model model);
    String editarUsuario(@ModelAttribute Usuario usuario, @RequestParam(required = false) String passwordActual);
    String eliminarUsuario(@PathVariable String username);
}