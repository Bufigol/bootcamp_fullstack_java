package com.bufigol.universidad.controlador.servlets;

import com.bufigol.universidad.dtos.autenticacion.LoginRequestDTO;
import com.bufigol.universidad.dtos.autenticacion.TokenResponseDTO;
import com.bufigol.universidad.interfaces.servicio.INT_AutenticacionServicio;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;

@Slf4j
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Autowired
    private INT_AutenticacionServicio autenticacionServicio;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Si el usuario ya está autenticado, redirigir a home
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("usuario") != null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        // Si no está autenticado, mostrar página de login
        request.getRequestDispatcher(request.getContextPath() +"/login.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Validar entrada
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Usuario y contraseña son requeridos");
            request.getRequestDispatcher(request.getContextPath() + "/login.html").forward(request, response);
            return;
        }

        try {
            // Crear DTO para la autenticación
            LoginRequestDTO loginRequest = new LoginRequestDTO(username, password);

            // Intentar autenticar
            TokenResponseDTO tokenResponse = autenticacionServicio.signin(loginRequest);

            // Si la autenticación es exitosa
            HttpSession session = request.getSession();
            session.setAttribute("usuario", username);
            session.setAttribute("token", tokenResponse.getToken());
            session.setAttribute("roles", tokenResponse.getRoles());

            // Establecer cookie con el token JWT
            // Nota: En producción, considerar usar cookies seguras y httpOnly
            response.addHeader("Authorization", "Bearer " + tokenResponse.getToken());

            // Redireccionar a la página principal
            response.sendRedirect(request.getContextPath() + "/home");

        } catch (BadCredentialsException e) {
            log.error("Error de autenticación para usuario {}: {}", username, e.getMessage());
            request.setAttribute("error", "Usuario o contraseña incorrectos");
            request.getRequestDispatcher("/WEB-INF/templates/login.html").forward(request, response);
        } catch (Exception e) {
            log.error("Error inesperado durante el login: {}", e.getMessage());
            request.setAttribute("error", "Error interno del servidor");
            request.getRequestDispatcher("/WEB-INF/templates/login.html").forward(request, response);
        }
    }
}