package com.bufigol.servlets;

import com.bufigol.dtos.usuario.UsuarioResponseDTO;
import com.bufigol.modelo.Usuario;
import com.bufigol.servicios.UsuarioServicio;
import com.bufigol.utils.PasswordGenerator;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "login", value = "/login")
public class ProcesarLogin extends HttpServlet {

    private UsuarioServicio usuarioServicio;


    public void init() {
        usuarioServicio = new UsuarioServicio();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        UsuarioResponseDTO user = usuarioServicio.buscarUsuarioPorUserName(userName);
        String pwd = user.getPassword();
        if(PasswordGenerator.verificarContrasena(password, pwd ) ){
            response.sendRedirect("/pgs/home.jsp");
            request.getSession().setAttribute("usuarioActivo", user.toModel());
        }else{
            Cookie error = new Cookie("invalidPwd", "true");
            error.setMaxAge(10);
            error.setPath("/");
            response.addCookie(error);
            response.sendRedirect("index.jsp");
        }

    }

    public void destroy() {
    }
}
