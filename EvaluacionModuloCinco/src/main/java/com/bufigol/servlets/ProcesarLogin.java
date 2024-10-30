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
            Cookie usrName = new Cookie("usrName", userName);
            usrName.setMaxAge(60 * 60);
            usrName.setPath("/");
            response.addCookie(usrName);
            response.sendRedirect("/pgs/home.jsp");
        }else{
            Cookie error = new Cookie("invalidPwd", "true");
            error.setMaxAge(60 * 60);
            error.setPath("/");
            response.addCookie(error);
            response.sendRedirect("index.jsp");
        }

    }

    public void destroy() {
    }
}
