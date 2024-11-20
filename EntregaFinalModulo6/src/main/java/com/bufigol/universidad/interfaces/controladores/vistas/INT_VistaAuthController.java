package com.bufigol.universidad.interfaces.controladores.vistas;

import com.bufigol.universidad.dtos.autenticacion.LoginRequestDTO;
import com.bufigol.universidad.dtos.autenticacion.SignupRequestDTO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Interfaz para el controlador de vistas de autenticación
 * Maneja las páginas de login y registro
 */
@RequestMapping("/auth")
public interface INT_VistaAuthController {

    /**
     * Muestra la página de login
     * @param model Modelo para la vista
     * @return nombre de la vista de login
     */
    @GetMapping("/login")
    String showLoginPage(Model model);

    /**
     * Procesa el formulario de login
     * @param loginRequest DTO con los datos de login
     * @param bindingResult Resultados de la validación
     * @param model Modelo para la vista
     * @param response HttpServletResponse para manejar cookies
     * @param redirectAttributes Para mensajes de redirección
     * @return redirección según resultado
     */
    @PostMapping("/login")
    String processLogin(
            @Valid @ModelAttribute("loginRequest") LoginRequestDTO loginRequest,
            BindingResult bindingResult,
            Model model,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes
    );

    /**
     * Muestra la página de registro
     * @param model Modelo para la vista
     * @return nombre de la vista de registro
     */
    @GetMapping("/register")
    String showRegisterPage(Model model);

    /**
     * Procesa el formulario de registro
     * @param signupRequest DTO con los datos de registro
     * @param bindingResult Resultados de la validación
     * @param model Modelo para la vista
     * @param redirectAttributes Para mensajes de redirección
     * @return redirección según resultado
     */
    @PostMapping("/register")
    String processRegistration(
            @Valid @ModelAttribute("signupRequest") SignupRequestDTO signupRequest,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    );

    /**
     * Procesa el logout
     * @param response HttpServletResponse para limpiar cookies
     * @param redirectAttributes Para mensajes de redirección
     * @return redirección a login
     */
    @GetMapping("/logout")
    String logout(
            HttpServletResponse response,
            RedirectAttributes redirectAttributes
    );

    /**
     * Añade atributos comunes a todas las vistas de autenticación
     * @param model Modelo para la vista
     */
    @ModelAttribute
    void addCommonAttributes(Model model);
}