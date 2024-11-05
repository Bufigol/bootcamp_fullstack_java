// ProcesaLogin.java
package com.bufigol.evm5s1;

import java.io.IOException;
import java.util.ArrayList;

import com.bufigol.modelo.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "login", value = "/login")
public class ProcesaLogin extends HttpServlet {
    private ArrayList<Usuario> usuarios;

    public void init() throws ServletException {
        super.init();
        this.usuarios = new ArrayList<Usuario>();
        this.usuarios.add(new Usuario("admin", "admin"));
        this.usuarios.add(new Usuario("user", "user"));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("login_username");
        String password = request.getParameter("login_password");

        if (username == null || password == null ||
                username.trim().isEmpty() || password.trim().isEmpty()) {
            response.sendRedirect("index.jsp?error=empty&tab=login");
            return;
        }

        Usuario user = new Usuario(username, password);
        boolean loginExitoso = false;

        for (Usuario u : this.usuarios) {
            if (u.equals(user)) {
                loginExitoso = true;
                break;
            }
        }

        if (loginExitoso) {
            // Crear cookies de sesión
            Cookie usernameCookie = new Cookie("username", username);
            Cookie lastLoginCookie = new Cookie("lastLogin",
                    String.valueOf(System.currentTimeMillis()));

            usernameCookie.setMaxAge(24 * 60 * 60);
            lastLoginCookie.setMaxAge(24 * 60 * 60);
            usernameCookie.setPath("/");
            lastLoginCookie.setPath("/");

            response.addCookie(usernameCookie);
            response.addCookie(lastLoginCookie);

            response.sendRedirect("other.jsp");
        } else {
            response.sendRedirect("index.jsp?error=invalid&tab=login");
        }
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}

