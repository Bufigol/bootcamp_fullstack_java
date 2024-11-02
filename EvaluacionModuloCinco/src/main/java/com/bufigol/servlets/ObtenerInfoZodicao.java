package com.bufigol.servlets;

import com.bufigol.servicios.HoroscopoServicio;
import com.bufigol.servicios.UsuarioServicio;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "infoUsuario", value = "/infoUsuario")
public class ObtenerInfoZodicao extends HttpServlet {
    private UsuarioServicio usuarioServicio;
    private HoroscopoServicio horoscopoServicio;


    @Override
    public void init() {
        this.usuarioServicio = new UsuarioServicio();
        this.horoscopoServicio = new HoroscopoServicio();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {

        }else {
            Cookie error = new Cookie("errorCookies", "true");
            error.setMaxAge(60);
            error.setPath("/");
            response.addCookie(error);
            request.getRequestDispatcher("/index.jsp");
        }
    }
}
