package com.bufigol.evm6s4.controlador;

import com.bufigol.evm6s4.interfaces.INT_UsuarioController;
import com.bufigol.evm6s4.interfaces.INT_UsuarioServicio;
import com.bufigol.evm6s4.modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsuarioController implements INT_UsuarioController {

    @Autowired
    private INT_UsuarioServicio usuarioServicio;

    @Override
    @GetMapping("/")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioServicio.listarUsuarios());
        model.addAttribute("titulo", "Lista de Usuarios");
        return "lista-usuarios";
    }

    @Override
    @GetMapping("/agregar")
    public String mostrarFormularioAgregar(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("titulo", "Agregar Usuario");
        model.addAttribute("accion", "agregar");
        return "usuario";
    }

    @Override
    @PostMapping("/agregar")
    public String agregarUsuario(@ModelAttribute Usuario usuario) {
        if (usuarioServicio.agregarUsuario(usuario)) {
            return "redirect:/?exito=Usuario agregado correctamente";
        } else {
            return "redirect:/agregar?error=Error en los datos ingresados";
        }
    }

    @Override
    @GetMapping("/editar/{username}")
    public String mostrarFormularioEditar(@PathVariable String username, Model model) {
        Usuario usuario = usuarioServicio.buscarPorUsername(username);

        if (usuario != null && !usuario.equals(new Usuario())) {
            String passwordActual = usuario.getPassword();
            usuario.setPassword("");
            model.addAttribute("usuario", usuario);
            model.addAttribute("titulo", "Editar Usuario");
            model.addAttribute("accion", "editar");
            model.addAttribute("passwordActual", passwordActual);
            return "usuario";
        } else {
            return "redirect:/?error=Usuario no encontrado";
        }
    }

    @Override
    @PostMapping("/editar")
    public String editarUsuario(@ModelAttribute Usuario usuario, @RequestParam(required = false) String passwordActual) {
        if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
            usuario.setPassword(passwordActual);
        }

        if (usuarioServicio.editarUsuario(usuario)) {
            return "redirect:/?exito=Usuario actualizado correctamente";
        } else {
            return "redirect:/editar/" + usuario.getUsername() + "?error=Error al actualizar los datos";
        }
    }

    @Override
    @GetMapping("/eliminar/{username}")
    public String eliminarUsuario(@PathVariable String username) {
        if (usuarioServicio.eliminarUsuario(username)) {
            return "redirect:/?exito=Usuario eliminado correctamente";
        } else {
            return "redirect:/?error=Error al eliminar el usuario";
        }
    }
}