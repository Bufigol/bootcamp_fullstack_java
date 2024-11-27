package com.bufigol.universidad.controlador.vistas;

import com.bufigol.universidad.dtos.modelo.AlumnoRequestDTO;
import com.bufigol.universidad.dtos.modelo.AlumnoResponseDTO;
import com.bufigol.universidad.dtos.modelo.MateriaResponseDTO;
import com.bufigol.universidad.interfaces.controladores.vistas.INT_VistaAlumnoController;
import com.bufigol.universidad.interfaces.servicio.INT_AlumnoServicio;
import com.bufigol.universidad.interfaces.servicio.INT_MateriasServicio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class VistaAlumnoController implements INT_VistaAlumnoController {

    private final INT_AlumnoServicio alumnoServicio;
    private final INT_MateriasServicio materiasServicio;

    @GetMapping("/admin/alumnos")
    public String showAlumnosPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        log.debug("Iniciando carga de página de alumnos. Page: {}, Size: {}", page, size);
        try {
            Page<AlumnoResponseDTO> alumnosPage = alumnoServicio.findAll(PageRequest.of(page, size));
            log.debug("Alumnos cargados: {}", alumnosPage.getTotalElements());
            model.addAttribute("alumnos", alumnosPage);
            model.addAttribute("currentPage", alumnosPage.getNumber());
            model.addAttribute("totalPages", alumnosPage.getTotalPages());
            return "alumnos/alumnos";
        } catch (Exception e) {
            log.error("Error al cargar la página de alumnos: {}", e.getMessage(), e);
            model.addAttribute("error", "Error al cargar la lista de alumnos");
            return "error";
        }
    }

    @Override
    public String showNuevoAlumnoModal(Model model) {
        log.debug("Mostrando modal de nuevo alumno");
        if (!model.containsAttribute("alumnoRequest")) {
            model.addAttribute("alumnoRequest", new AlumnoRequestDTO());
        }
        return "alumnos/modal-nuevo :: formularioAlumno";
    }

    @Override
    @Transactional
    public String processAlumnoSubmit(
            @Valid @ModelAttribute("alumnoRequest") AlumnoRequestDTO alumnoRequest,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        log.debug("Procesando formulario de alumno: {}", alumnoRequest);

        if (result.hasErrors()) {
            return "alumnos/modal-nuevo :: formularioAlumno";
        }

        try {
            AlumnoResponseDTO createdAlumno = alumnoServicio.create(alumnoRequest);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Alumno creado exitosamente");
            return "redirect:/admin/alumnos";
        } catch (Exception e) {
            log.error("Error al crear alumno: {}", e.getMessage());
            model.addAttribute("error", "Error al crear el alumno: " + e.getMessage());
            return "alumnos/modal-nuevo :: formularioAlumno";
        }
    }

    @Override
    public String showEditAlumnoModal(@PathVariable Long id, Model model) {
        log.debug("Mostrando modal de edición para alumno ID: {}", id);
        try {
            Optional<AlumnoResponseDTO> alumno = alumnoServicio.findById(id);
            if (alumno.isEmpty()) {
                model.addAttribute("error", "Alumno no encontrado");
                return "error";
            }
            model.addAttribute("alumno", alumno.get());
            return "alumnos/modal-editar :: formularioEditar";
        } catch (Exception e) {
            log.error("Error al cargar alumno para edición: {}", e.getMessage());
            model.addAttribute("error", "Error al cargar el alumno");
            return "error";
        }
    }

    @Override
    public String showMateriasAlumnoModal(@PathVariable Long id, Model model) {
        log.debug("Mostrando modal de materias para alumno ID: {}", id);
        try {
            Optional<AlumnoResponseDTO> alumno = alumnoServicio.findById(id);
            if (alumno.isEmpty()) {
                model.addAttribute("error", "Alumno no encontrado");
                return "error";
            }

            List<MateriaResponseDTO> materias = materiasServicio.findByAlumnoId(id);
            model.addAttribute("alumno", alumno.get());
            model.addAttribute("materias", materias);
            return "alumnos/modal-materias :: listaMaterias";
        } catch (Exception e) {
            log.error("Error al cargar materias del alumno: {}", e.getMessage());
            model.addAttribute("error", "Error al cargar las materias");
            return "error";
        }
    }

    @Override
    public String searchAlumnos(String rut, String nombre, int page, Model model) {
        log.debug("Buscando alumnos por rut: {} y nombre: {}", rut, nombre);
        try {
            Page<AlumnoResponseDTO> resultados;
            if (rut != null && !rut.isEmpty()) {
                Optional<AlumnoResponseDTO> alumno = alumnoServicio.findByRut(rut);
                resultados = alumno.map(a -> new PageImpl<>(List.of(a), PageRequest.of(0, 1), 1))
                        .orElseGet(() -> new PageImpl<>(List.of(), PageRequest.of(0, 1), 0));
            } else if (nombre != null && !nombre.isEmpty()) {
                List<AlumnoResponseDTO> alumnos = alumnoServicio.findByNombreContaining(nombre);
                resultados = new PageImpl<>(alumnos, PageRequest.of(page, 10), alumnos.size());
            } else {
                resultados = alumnoServicio.findAll(PageRequest.of(page, 10));
            }

            model.addAttribute("alumnos", resultados);
            model.addAttribute("currentPage", resultados.getNumber());
            model.addAttribute("totalPages", resultados.getTotalPages());
            return "alumnos/lista :: tablaAlumnos";
        } catch (Exception e) {
            log.error("Error en la búsqueda de alumnos: {}", e.getMessage());
            model.addAttribute("error", "Error al realizar la búsqueda");
            return "error";
        }
    }

    @Override
    @Transactional
    public String deleteAlumno(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        log.debug("Eliminando alumno ID: {}", id);
        try {
            alumnoServicio.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Alumno eliminado exitosamente");
            return "redirect:/admin/alumnos";
        } catch (Exception e) {
            log.error("Error al eliminar alumno: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar el alumno");
            return "redirect:/admin/alumnos";
        }
    }

    @Override
    @ModelAttribute
    public void addCommonAttributes(Model model) {
        model.addAttribute("currentYear", java.time.Year.now().getValue());
        model.addAttribute("seccionActual", "alumnos");
    }
}