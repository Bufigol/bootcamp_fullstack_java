// ProcesarRegistro.java
package com.bufigol.evm5s1;

import com.bufigol.modelo.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "registro", value = "/registro")
public class ProcesarRegistro extends HttpServlet {
    private ArrayList<Usuario> usuarios;

    @Override
    public void init() throws ServletException {
        super.init();
        // Obtener la referencia a la lista de usuarios del servlet de login
        ProcesaLogin loginServlet = (ProcesaLogin) getServletContext()
                .getAttribute("loginServlet");
        if (loginServlet != null) {
            this.usuarios = loginServlet.getUsuarios();
        } else {
            this.usuarios = new ArrayList<>();
            this.usuarios.add(new Usuario("admin", "admin"));
            this.usuarios.add(new Usuario("user", "user"));
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("password_conmfirm");

        // Validaciones
        if (username == null || password == null || confirmPassword == null ||
                username.trim().isEmpty() || password.trim().isEmpty()) {
            response.sendRedirect("index.jsp?error=empty&tab=register");
            return;
        }

        if (!password.equals(confirmPassword)) {
            response.sendRedirect("index.jsp?error=mismatch&tab=register");
            return;
        }

        // Verificar si el usuario ya existe
        if (usuarioExiste(username)) {
            response.sendRedirect("index.jsp?error=exists&tab=register");
            return;
        }

        // Crear nuevo usuario
        Usuario nuevoUsuario = new Usuario(username, password);
        usuarios.add(nuevoUsuario);

        // Actualizar cookies
        Cookie totalUsuariosCookie = new Cookie("totalUsuarios",
                String.valueOf(usuarios.size()));
        Cookie ultimoRegistroCookie = new Cookie("ultimoRegistro", username);

        totalUsuariosCookie.setMaxAge(24 * 60 * 60);
        ultimoRegistroCookie.setMaxAge(24 * 60 * 60);
        totalUsuariosCookie.setPath("/");
        ultimoRegistroCookie.setPath("/");

        response.addCookie(totalUsuariosCookie);
        response.addCookie(ultimoRegistroCookie);

        // Redireccionar al login con mensaje de éxito
        response.sendRedirect("index.jsp?registered=true&tab=login");
    }

    private boolean usuarioExiste(String username) {
        for (Usuario u : usuarios) {
            if (u.getNombreUsuario().equals(username)) {
                return true;
            }
        }
        return false;
    }
}