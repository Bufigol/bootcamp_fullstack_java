package com.bufigol.universidad.controlador.vistas;

import com.bufigol.universidad.dtos.autenticacion.LoginRequestDTO;
import com.bufigol.universidad.dtos.autenticacion.SignupRequestDTO;
import com.bufigol.universidad.dtos.autenticacion.TokenResponseDTO;
import com.bufigol.universidad.interfaces.controladores.vistas.INT_VistaAuthController;
import com.bufigol.universidad.interfaces.servicio.INT_AutenticacionServicio;
import com.bufigol.universidad.seguridad.jwt.JwtTokenProvider;
import com.bufigol.universidad.servicio.UsuarioServicio;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class VistaAuthController implements INT_VistaAuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private INT_AutenticacionServicio autenticacionServicio;

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        log.debug("Mostrando página de login");

        if (!model.containsAttribute("loginRequest")) {
            model.addAttribute("loginRequest", new LoginRequestDTO());
        }
        return "auth/login";
    }

    @PostMapping("/login")
    public String processLogin(
            @Valid @ModelAttribute("loginRequest") LoginRequestDTO loginRequest,
            BindingResult bindingResult,
            Model model,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes) {

        log.debug("Procesando login para usuario: {}", loginRequest.getUsername());

        if (bindingResult.hasErrors()) {
            return "auth/login";
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generar JWT token
            List<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            String token = jwtTokenProvider.createToken(loginRequest.getUsername(), roles);

            // Agregar token como cookie
            Cookie jwtCookie = new Cookie("JWT", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            response.addCookie(jwtCookie);

            redirectAttributes.addFlashAttribute("successMessage", "Login exitoso");
            return "redirect:/home";

        } catch (AuthenticationException e) {
            log.error("Error de autenticación: {}", e.getMessage());
            model.addAttribute("error", "Usuario o contraseña inválidos");
            return "auth/login";
        }
    }

    @Override
    public String showRegisterPage(Model model) {
        if (!model.containsAttribute("signupRequest")) {
            model.addAttribute("signupRequest", new SignupRequestDTO());
        }
        return "auth/register";
    }

    @Override
    public String processRegistration(SignupRequestDTO signupRequest,
                                      BindingResult bindingResult,
                                      Model model,
                                      RedirectAttributes redirectAttributes) {
        log.debug("Procesando registro para usuario: {}", signupRequest.getUsername());

        try {
            if (bindingResult.hasErrors()) {
                return "auth/register";
            }

            // Verificar si las contraseñas coinciden
            if (!signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {
                bindingResult.rejectValue("confirmPassword", "error.confirmPassword",
                        "Las contraseñas no coinciden");
                return "auth/register";
            }

            // Intentar registrar al usuario
            TokenResponseDTO response = autenticacionServicio.signup(signupRequest);

            log.info("Registro exitoso para usuario: {}", signupRequest.getUsername());
            redirectAttributes.addFlashAttribute("successMessage",
                    "Registro exitoso. Por favor, inicie sesión");

            return "redirect:/auth/login";

        } catch (Exception e) {
            log.error("Error durante el registro: {}", e.getMessage());
            model.addAttribute("error", "Error al procesar el registro: " + e.getMessage());
            return "auth/register";
        }
    }

    @Override
    public String logout(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        // Limpiar el contexto de seguridad
        SecurityContextHolder.clearContext();

        // Eliminar la cookie JWT
        Cookie cookie = new Cookie("JWT", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        redirectAttributes.addFlashAttribute("successMessage", "Sesión cerrada exitosamente");
        return "redirect:/auth/login";
    }

    @Override
    public void addCommonAttributes(Model model) {
        model.addAttribute("currentYear", java.time.Year.now().getValue());
        model.addAttribute("appName", "Sistema de Calificaciones");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            model.addAttribute("isAuthenticated", true);
            model.addAttribute("username", auth.getName());
            model.addAttribute("roles", auth.getAuthorities());
        }
    }
}