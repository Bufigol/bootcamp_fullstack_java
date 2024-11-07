package com.bufigol.ecm6s3.controlador;

import com.bufigol.ecm6s3.modelo.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {
    @GetMapping("/form")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "formulario";  // nombre de la plantilla del formulario
    }

    @PostMapping("/user")
    public String procesarFormulario(@ModelAttribute Usuario usuario) {
        return "resultado";  // nombre de la plantilla de resultado
    }
}
