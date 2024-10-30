package com.bufigol.servlets;

import com.bufigol.dtos.usuario.UsuarioCreateDto;
import com.bufigol.dtos.usuario.UsuarioResponseDTO;
import com.bufigol.modelo.Usuario;
import com.bufigol.servicios.UsuarioServicio;
import com.bufigol.utils.Comprobadores;
import com.bufigol.utils.ControlFechas;
import com.bufigol.utils.PasswordGenerator;
import com.bufigol.utils.UtilidadesCookies;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;

@WebServlet(name = "register", value = "/register")
public class ProcesarRegistro extends HttpServlet {

    private UsuarioServicio usuarioServicio;

    public void init() {
        usuarioServicio = new UsuarioServicio();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String userName = request.getParameter("userName");
        String nombre = request.getParameter("nombre");
        String email = request.getParameter( "email");

        String  fn = request.getParameter("fechaNacimiento");

        Date fechaNacimiento = ControlFechas.dateFromString(fn);

        String password = request.getParameter("password");
        String confirmarPassword = request.getParameter("confirmarPassword");

        ArrayList<String> mensajesDeError = comprobarErrores(userName, nombre, email, fechaNacimiento, password, confirmarPassword);
        if(mensajesDeError.isEmpty()){
            Usuario user = new Usuario();
            user.setUserName(userName);
            user.setNombre(nombre);
            user.setEmail(email);
            user.setFechaNacimiento(fechaNacimiento);
            user.setPassword(PasswordGenerator.generarContrasenaSegura(password));
            UsuarioCreateDto createDto = new UsuarioCreateDto(user);

            usuarioServicio.crearUsuario(createDto);

            Cookie usuario = new Cookie("usuarioID", String.valueOf( createDto.getId() ) );
            Cookie usrName = new Cookie("usrName", createDto.getUsername() );

            usrName.setMaxAge(60 * 60);
            usuario.setMaxAge(60 * 60);

            response.addCookie(usrName);
            response.addCookie(usuario);

            response.sendRedirect("index.jsp");
        }else{
            Cookie errores = UtilidadesCookies.stringArrayListToCookie(mensajesDeError,"errores");
            errores.setPath("/"); // Establecer el path para que la cookie esté disponible en todo el contexto
            // Agregar la cookie a la respuesta
            response.addCookie(errores);
            // Redirigir a otro JSP
            response.sendRedirect("register.jsp");
        }
    }

    public void destroy() {
    }


    private ArrayList<String>  comprobarErrores(String userName, String nombre, String email, Date fechaNacimiento, String password, String confirmarPassword) {
        ArrayList<String> mensajesDeError = new ArrayList<>();
        if(usuarioServicio.buscarUsuarioPorUserName(userName).getId()>0){
            mensajesDeError.add("Usuario ya existe");
        }else{
            if(userName.isBlank() || userName.isEmpty()){
                mensajesDeError.add("El campo de usuario no puede estar vacio");
            } else {
                if(userName.length() >30){
                    mensajesDeError.add("El nombre de usuario es muy largo");
                }
            }
        }

        if(nombre.isEmpty() || nombre.isBlank()){
            mensajesDeError.add("El campo de nombre no puede estar vacio");
        }else {
            if(nombre.length() >30){
                mensajesDeError.add("El campo de nombre es muy largo");
            }
        }

        if(!Comprobadores.esEmailValido(email)){
            mensajesDeError.add("El campo e-mail no es valido");
        }

        Date hoy = new Date(System.currentTimeMillis());
        if (fechaNacimiento.after(hoy)) {
            mensajesDeError.add("No se debe seleccionar fechas en el futuro");
        }

        if(password.isBlank() || password.isEmpty() ){
            mensajesDeError.add("Debe ingresar una password");
        }else{
            if(confirmarPassword.isEmpty() || confirmarPassword.isBlank() ){
                mensajesDeError.add("Por favor confirme la contrseña");
            }else{
                if( !password.equals(confirmarPassword) ){
                    mensajesDeError.add("Las contraseñas no coinciden.");
                }
            }
        }
        return mensajesDeError;
    }
}
