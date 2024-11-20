package com.bufigol.universidad.interfaces.controladores.rest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

public interface INT_HomeController {

    /**
     * Muestra la página principal
     * @param model Modelo para pasar datos a la vista
     * @return nombre de la vista home
     */
    @GetMapping("/home")
    String showHomePage(Model model);

    /**
     * Muestra la página de inicio
     * @return nombre de la vista index o redirección
     */
    @GetMapping("/")
    String index();

    /**
     * Muestra la página de login
     * @return nombre de la vista login
     */
    @GetMapping("/login")
    String showLoginPage(Model model);

    /**
     * Muestra la página de registro
     * @return nombre de la vista registro
     */
    @GetMapping("/registro")
    String showRegistroPage();

    /**
     * Muestra el perfil del usuario
     * @param model Modelo para pasar datos a la vista
     * @param principal Usuario autenticado actual
     * @return nombre de la vista perfil
     */
    @GetMapping("/perfil")
    String showPerfilPage(Model model, Principal principal);

    /**
     * Muestra la página de materias con paginación
     * @param model Modelo para pasar datos a la vista
     * @param page Número de página actual
     * @param size Tamaño de la página
     * @return nombre de la vista materias
     */
    @GetMapping("/materias")
    String showMateriasPage(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    );

    /**
     * Muestra el panel de administración
     * @param model Modelo para pasar datos a la vista
     * @return nombre de la vista admin/dashboard
     */
    @GetMapping("/admin/dashboard")
    String showDashboardPage(Model model);

    /**
     * Maneja los errores de acceso denegado
     * @param model Modelo para pasar datos a la vista
     * @return nombre de la vista error
     */
    @GetMapping("/error/403")
    String handleAccessDenied(Model model);
}