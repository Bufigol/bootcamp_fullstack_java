package com.bufigol.evm5s1;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.bufigol.modelo.Usuario;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "login", value = "/login")
public class ProcesaLogin extends HttpServlet{
    private ArrayList<Usuario> usuarios;


    public void init(){
        this.usuarios = new ArrayList<Usuario>();
        this.usuarios.add(new Usuario("admin", "admin"));
        this.usuarios.add(new Usuario("user", "user"));
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //login_username

        String username = request.getParameter("login_username");
        String password = request.getParameter("login_password");
        Cookie galleta = new Cookie("usuarios", this.usuarios.toString());
        Usuario user = new Usuario(username, password);
        for(Usuario u : this.usuarios){
            if(u.equals(user)){
                response.sendRedirect("other.jsp");
            }else{
                response.getWriter().println("Login incorrecto");
                response.sendRedirect("index.jsp");
            }
        }
    }
}
