package com.bufigol.servlets;

import com.bufigol.dtos.usuario.UsuarioResponseDTO;
import com.bufigol.modelo.Usuario;
import com.bufigol.servicios.UsuarioServicio;
import com.bufigol.utils.UtilidadesCookies;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

public class ProcesarEliminarCuenta extends HttpServlet {
    private UsuarioServicio usuarioServicio;


    @Override
    public void init() {
        this.usuarioServicio = new UsuarioServicio();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Usuario usuarioActivo = (Usuario) request.getSession().getAttribute("usuarioActivo");
        int id = usuarioActivo.getId();
        this.usuarioServicio.eliminarUsuario(id);
        UtilidadesCookies.eliminarTodasLasCookies(request, response);
        request.getSession().invalidate();
        Cookie cuentaEliminada = new Cookie("cuentaEliminada", "true");
        cuentaEliminada.setMaxAge(10);
        cuentaEliminada.setPath("/");
        response.addCookie(cuentaEliminada);
        response.sendRedirect("/index.jsp");
    }
}