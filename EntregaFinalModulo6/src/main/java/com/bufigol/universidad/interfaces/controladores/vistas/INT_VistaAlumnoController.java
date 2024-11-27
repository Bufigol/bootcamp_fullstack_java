package com.bufigol.universidad.interfaces.controladores.vistas;

import com.bufigol.universidad.dtos.modelo.AlumnoRequestDTO;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface INT_VistaAlumnoController {

    @GetMapping("/admin/alumnos")
    String showAlumnosPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model);

    @GetMapping("/admin/alumnos/nuevo")
    String showNuevoAlumnoModal(Model model);

    @PostMapping("/admin/alumnos")
    String processAlumnoSubmit(
            @Valid @ModelAttribute("alumnoRequest") AlumnoRequestDTO alumnoRequest,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes);

    @GetMapping("/admin/alumnos/{id}/edit")
    String showEditAlumnoModal(@PathVariable Long id, Model model);

    @GetMapping("/admin/alumnos/{id}/materias")
    String showMateriasAlumnoModal(@PathVariable Long id, Model model);

    @GetMapping("/admin/alumnos/search")
    String searchAlumnos(
            @RequestParam(required = false) String rut,
            @RequestParam(required = false) String nombre,
            @RequestParam(defaultValue = "0") int page,
            Model model);

    @DeleteMapping("/admin/alumnos/{id}")
    String deleteAlumno(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes);

    @ModelAttribute
    void addCommonAttributes(Model model);
}