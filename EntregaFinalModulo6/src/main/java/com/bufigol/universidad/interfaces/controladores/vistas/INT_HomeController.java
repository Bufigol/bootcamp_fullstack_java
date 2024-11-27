package com.bufigol.universidad.interfaces.controladores.vistas;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Interfaz que define los endpoints relacionados con las páginas principales y navegación
 * del sistema de calificaciones universitarias.
 */
public interface INT_HomeController {

    /**
     * Muestra la página principal/dashboard personalizada según el usuario autenticado
     * @param userDetails Detalles del usuario autenticado
     * @param model Modelo para pasar datos a la vista
     * @return nombre de la vista
     */
    @GetMapping("/home")
    String showHomePage(@AuthenticationPrincipal UserDetails userDetails, Model model);

    /**
     * Muestra la página de inicio pública
     * @return nombre de la vista index o redirección según el estado de autenticación
     */
    @GetMapping("/")
    String index();

    /**
     * Muestra la página de login
     * @param model Modelo para pasar datos a la vista
     * @return nombre de la vista login o redirección si ya está autenticado
     */
    @GetMapping("/login")
    String showLoginPage(Model model);

    /**
     * Muestra la página de registro
     * @return nombre de la vista registro o redirección si ya está autenticado
     */
    @GetMapping("/registro")
    String showRegistroPage();

    /**
     * Muestra el perfil del usuario autenticado
     * @param model Modelo para pasar datos a la vista
     * @param principal Usuario autenticado actual
     * @return nombre de la vista perfil
     */
    @GetMapping("/perfil")
    String showPerfilPage(Model model, Principal principal);

    /**
     * Muestra la página de materias con paginación
     * @param model Modelo para pasar datos a la vista
     * @param page Número de página actual (por defecto 0)
     * @param size Tamaño de la página (por defecto 10)
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

    /**
     * Maneja errores generales de la aplicación
     * @param model Modelo para pasar datos a la vista
     * @return nombre de la vista error
     */
    @GetMapping("/error")
    String handleError(Model model);

    /**
     * Método utilitario para calcular el promedio de materias por alumno
     * @param alumnos Lista de alumnos para calcular el promedio
     * @return promedio de materias por alumno
     */
    default double calculatePromedioMaterias(java.util.List<com.bufigol.universidad.dtos.modelo.AlumnoResponseDTO> alumnos) {
        if (alumnos == null || alumnos.isEmpty()) {
            return 0.0;
        }
        return alumnos.stream()
                .filter(alumno -> alumno.getMaterias() != null)
                .mapToDouble(alumno -> alumno.getMaterias().size())
                .average()
                .orElse(0.0);
    }
}