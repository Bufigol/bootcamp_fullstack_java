package com.bufigol.servlets;

import com.bufigol.dtos.usuario.UsuarioResponseDTO;
import com.bufigol.modelo.Usuario;
import com.bufigol.servicios.UsuarioServicio;
import jakarta.servlet.annotation.WebServlet;
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
        String username = request.getParameter("username");
        UsuarioResponseDTO requestUser = usuarioServicio.buscarUsuarioPorUserName(username);
    }

    public void destroy() {
    }
}
