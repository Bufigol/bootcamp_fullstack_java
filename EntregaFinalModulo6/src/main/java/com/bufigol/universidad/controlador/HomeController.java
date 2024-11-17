package com.bufigol.universidad.controlador;

import com.bufigol.universidad.dtos.modelo.AlumnoResponseDTO;
import com.bufigol.universidad.dtos.modelo.MateriaResponseDTO;
import com.bufigol.universidad.excepciones.seguridad.JwtAuthenticationException;
import com.bufigol.universidad.excepciones.servicio.ResourceNotFoundException;
import com.bufigol.universidad.interfaces.controladores.INT_HomeController;
import com.bufigol.universidad.interfaces.servicio.INT_AlumnoServicio;
import com.bufigol.universidad.interfaces.servicio.INT_MateriasServicio;
import com.bufigol.universidad.interfaces.servicio.INT_UsuarioServicio;
import com.bufigol.universidad.modelo.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController implements INT_HomeController {

    private final INT_AlumnoServicio alumnoServicio;
    private final INT_MateriasServicio materiasServicio;
    private final INT_UsuarioServicio usuarioServicio;

    @Override
    @GetMapping("/home")
    public String showHomePage(Model model) {
        log.debug("Mostrando página de inicio");
        try {
            Page<AlumnoResponseDTO> ultimosAlumnos = alumnoServicio.findAll(PageRequest.of(0, 5));
            model.addAttribute("ultimosAlumnos", ultimosAlumnos.getContent());
            model.addAttribute("paginaActual", "home");

            return "home";
        } catch (Exception e) {
            handleError(model, "Error al cargar la página de inicio", e);
            return "error";
        }
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @Override
    @GetMapping("/login")
    public String showLoginPage() {
        log.debug("Mostrando página de login");
        return "login";
    }

    @Override
    @GetMapping("/registro")
    public String showRegistroPage() {
        log.debug("Mostrando página de registro");
        return "registro";
    }

    @Override
    @GetMapping("/perfil")
    public String showPerfilPage(Model model, Principal principal) {
        log.debug("Mostrando página de perfil para usuario: {}", principal.getName());
        try {
            Usuario usuario = usuarioServicio.findByUsername(principal.getName())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Usuario", "username", principal.getName()));

            model.addAttribute("usuario", usuario);

            // Si el usuario es un alumno, buscar sus materias
            if (usuario.getRoles().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_USER"))) {

                AlumnoResponseDTO alumno = alumnoServicio.findByRut(usuario.getUsername())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Alumno", "rut", usuario.getUsername()));

                model.addAttribute("alumno", alumno);
                model.addAttribute("materias", alumno.getMaterias());
            }

            // Verificar si hay mensaje de éxito en los parámetros
            String successParam = (String) model.getAttribute("success");
            if (successParam != null) {
                model.addAttribute("successMessage", "Operación realizada con éxito");
            }

            model.addAttribute("paginaActual", "perfil");
            return "perfil";

        } catch (ResourceNotFoundException e) {
            log.error("Error al cargar perfil: {}", e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "error";
        } catch (Exception e) {
            handleError(model, "Error inesperado al cargar el perfil", e);
            return "error";
        }
    }

    @Override
    @GetMapping("/materias")
    public String showMateriasPage(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.debug("Mostrando página de materias, página: {}, tamaño: {}", page, size);
        try {
            Page<MateriaResponseDTO> materias = materiasServicio.findAll(PageRequest.of(page, size));

            if (materias.isEmpty() && page > 0) {
                throw new ResourceNotFoundException("Materias", "página", page);
            }

            model.addAttribute("materias", materias.getContent());
            model.addAttribute("paginaActual", materias.getNumber());
            model.addAttribute("totalPaginas", materias.getTotalPages());
            model.addAttribute("totalElementos", materias.getTotalElements());
            model.addAttribute("size", size);

            model.addAttribute("seccionActual", "materias");

            return "materias";
        } catch (ResourceNotFoundException e) {
            log.warn("No se encontraron materias en la página {}: {}", page, e.getMessage());
            model.addAttribute("warning", e.getMessage());
            return "materias";
        } catch (Exception e) {
            handleError(model, "Error al cargar la página de materias", e);
            return "error";
        }
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFoundException(
            ResourceNotFoundException ex,
            Model model) {
        log.warn("Recurso no encontrado: {}", ex.getMessage());
        model.addAttribute("error", ex.getMessage());
        model.addAttribute("tipo", "No encontrado");
        return "error";
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String handleBadCredentialsException(
            BadCredentialsException ex,
            Model model) {
        log.warn("Error de credenciales: {}", ex.getMessage());
        model.addAttribute("error", "Credenciales inválidas");
        model.addAttribute("tipo", "Error de autenticación");
        return "login";
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    public String handleJwtAuthenticationException(
            JwtAuthenticationException ex,
            Model model) {
        log.warn("Error de autenticación JWT: {}", ex.getMessage());
        model.addAttribute("error", "Su sesión ha expirado. Por favor, inicie sesión nuevamente.");
        return "login";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(
            Exception ex,
            Model model) {
        log.error("Error no manejado: ", ex);
        model.addAttribute("error", "Ha ocurrido un error inesperado");
        model.addAttribute("detalles", ex.getMessage());
        return "error";
    }

    private void handleError(Model model, String message, Exception e) {
        log.error(message, e);
        model.addAttribute("error", message);
        model.addAttribute("detalles", e.getMessage());
        model.addAttribute("tipo", e.getClass().getSimpleName());

        if (isDevelopmentEnvironment()) {
            model.addAttribute("stackTrace", e.getStackTrace());
            model.addAttribute("causa", e.getCause() != null ? e.getCause().getMessage() : "No disponible");
        }
    }

    private boolean isDevelopmentEnvironment() {
        try {
            String activeProfile = System.getProperty("spring.profiles.active");
            return activeProfile != null && activeProfile.contains("dev");
        } catch (Exception e) {
            return false;
        }
    }
}