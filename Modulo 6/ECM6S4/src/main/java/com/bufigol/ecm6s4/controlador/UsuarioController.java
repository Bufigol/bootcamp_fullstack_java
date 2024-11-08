package com.bufigol.ecm6s4.controlador;

import com.bufigol.ecm6s4.modelo.Usuario;
import com.bufigol.ecm6s4.servicio.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registro")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String procesarRegistro(@ModelAttribute Usuario usuario, Model model) {
        if (usuarioService.validarUsuario(usuario)) {
            usuarioService.registrarUsuario(usuario);
            model.addAttribute("usuario", usuario);
            return "detalles-usuario";
        } else {
            model.addAttribute("mensaje", "El registro ha fallado");
            model.addAttribute("usuario", usuario); // Mantiene los datos ingresados
            return "registro"; // Se mantiene en la misma página si hay error
        }
    }
}