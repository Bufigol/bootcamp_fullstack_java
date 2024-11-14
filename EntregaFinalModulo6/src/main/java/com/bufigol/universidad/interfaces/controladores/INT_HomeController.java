package com.bufigol.universidad.interfaces.controladores;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
public interface INT_HomeController {

    @GetMapping("/")
    String showHomePage(Model model);

    @GetMapping("/login")
    String showLoginPage();

    @GetMapping("/registro")
    String showRegistroPage();

    @GetMapping("/perfil")
    String showPerfilPage(Model model, Principal principal);

    @GetMapping("/materias")
    String showMateriasPage(Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size);
}
