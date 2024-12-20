package com.bufigol.universidad.controlador.vistas;

import com.bufigol.universidad.dtos.modelo.AlumnoResponseDTO;
import com.bufigol.universidad.dtos.modelo.MateriaResponseDTO;
import com.bufigol.universidad.interfaces.controladores.vistas.INT_HomeController;
import com.bufigol.universidad.interfaces.servicio.INT_AlumnoServicio;
import com.bufigol.universidad.interfaces.servicio.INT_MateriasServicio;
import com.bufigol.universidad.interfaces.servicio.INT_UsuarioServicio;
import com.bufigol.universidad.modelo.Usuario;
import com.bufigol.universidad.utils.Comprobadores;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController implements INT_HomeController {

    private final INT_UsuarioServicio usuarioServicio;
    private final INT_AlumnoServicio alumnoServicio;
    private final INT_MateriasServicio materiasServicio;

    // Agregar clase interna para DashboardStats
    @Data
    @AllArgsConstructor
    private static class DashboardStats {
        private long totalAlumnos;
        private long totalMaterias;
        private double promedioMateriasXAlumno;
    }


    @Override
    @GetMapping("/")
    public String index() {
        if (isAuthenticated()) {
            return "redirect:/home";
        }
        return "index";
    }

    @Override
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        if (isAuthenticated()) {
            return "redirect:/home";
        }
        return "auth/login";
    }

    @Override
    @GetMapping("/registro")
    public String showRegistroPage() {
        if (isAuthenticated()) {
            return "redirect:/home";
        }
        return "auth/registro";
    }

    @Override
    @GetMapping("/home")
    public String showHomePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        try {
            if (userDetails == null) {
                return "redirect:/login";
            }

            Usuario usuario =
                    usuarioServicio.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

            Page<AlumnoResponseDTO> ultimosAlumnos = alumnoServicio.findAll(PageRequest.of(0, 5));
            Page<MateriaResponseDTO> ultimasMaterias = materiasServicio.findAll(PageRequest.of(0, 5));

            DashboardStats stats = new DashboardStats(
                    ultimosAlumnos.getTotalElements(),
                    ultimasMaterias.getTotalElements(),
                    calculatePromedioMaterias(ultimosAlumnos.getContent())
            );

            model.addAttribute("usuario", usuario);
            model.addAttribute("ultimosAlumnos", ultimosAlumnos.getContent());
            model.addAttribute("ultimasMaterias", ultimasMaterias.getContent());
            model.addAttribute("stats", stats);
            model.addAttribute("seccionActual", "dashboard");

            return "home";

        } catch (Exception e) {
            log.error("Error al cargar el dashboard: {}", e.getMessage());
            model.addAttribute("error", "Error al cargar el dashboard");
            return "error";
        }
    }

    @GetMapping("/perfil")
    public String showPerfilPage(Model model, Principal principal) {
        try {
            // Obtener el usuario actual
            Optional<Usuario> usuario = usuarioServicio.findByUsername(principal.getName());
            if (usuario.isPresent()) {
                model.addAttribute("usuario", usuario.get());

                // Solo intentar obtener datos de alumno si el username tiene formato de RUT
                if (Comprobadores.isValidRUT(principal.getName())) {
                    Optional<AlumnoResponseDTO> alumno = alumnoServicio.findByRut(principal.getName());
                    alumno.ifPresent(a -> model.addAttribute("alumno", a));
                }

                return "perfil";
            } else {
                model.addAttribute("errorMessage", "No se encontró la información del usuario");
                return "error";
            }
        } catch (Exception e) {
            log.error("Error al cargar el perfil: {}", e.getMessage());
            model.addAttribute("errorMessage", "Error al cargar el perfil");
            return "error";
        }
    }

    @Override
    @GetMapping("admin/materias")
    public String showMateriasPage(Model model, @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        return "admin/materias";
    }

    @Override
    @GetMapping("/admin/dashboard")
    public String showDashboardPage(Model model) {
        return "admin/dashboard";
    }

    @Override
    public String handleAccessDenied(Model model) {
        return "";
    }

    @Override
    @GetMapping("/error")
    public String handleError(Model model) {
        model.addAttribute("error", "Ha ocurrido un error inesperado");
        return "error";
    }

    @Override
    public double calculatePromedioMaterias(List<AlumnoResponseDTO> alumnos) {
        return INT_HomeController.super.calculatePromedioMaterias(alumnos);
    }



    private boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser");
    }
}