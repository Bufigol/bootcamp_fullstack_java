package com.bufigol.servlets;

import com.bufigol.servicios.UsuarioServicio;
import com.bufigol.utils.UtilidadesCookies;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


public class ProcesarLogout extends HttpServlet {
    @Override
    public void init() {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Obtener todas las cookies de la solicitud
        Cookie[] cookies = request.getCookies();
        UtilidadesCookies.eliminarTodasLasCookies(request, response);

        // Cerrar la sesión
        request.getSession().invalidate();

        // Redirigir a la página de inicio o de login
        response.sendRedirect(request.getContextPath() + "/index.jsp");

    }
}
