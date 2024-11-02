package com.bufigol.servlets;

import com.bufigol.servicios.UsuarioServicio;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "logout", value = "/logout")
public class ProcesarLogout extends HttpServlet {
    @Override
    public void init() {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Obtener todas las cookies de la solicitud
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // Establecer el Max-Age a 0 para eliminar la cookie
                cookie.setMaxAge(0);
                cookie.setPath("/"); // Asegúrate de que el path sea correcto
                response.addCookie(cookie); // Añadir la cookie con Max-Age 0 a la respuesta
            }
        }

        // Redirigir a la página de inicio o de login
        response.sendRedirect(request.getContextPath() + "/index.jsp");

    }
}
