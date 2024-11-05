package com.bufigol.servlets;

import com.bufigol.dtos.usuario.UsuarioUpdateDTO;
import com.bufigol.modelo.Horoscopo;
import com.bufigol.modelo.Usuario;
import com.bufigol.servicios.HoroscopoServicio;
import com.bufigol.servicios.UsuarioServicio;
import com.bufigol.utils.Comprobadores;
import com.bufigol.utils.ControlFechas;
import com.bufigol.utils.PasswordGenerator;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;

@WebServlet(name = "update", value = "/ActualizarUsuario")
public class ActualizarUsuario extends HttpServlet {
    private UsuarioServicio usuarioServicio;
    private HoroscopoServicio horoscopoServicio;

    @Override
    public void init() {
        this.usuarioServicio = new UsuarioServicio();
        this.horoscopoServicio = new HoroscopoServicio();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean errores = false;
        String mensajeError = "";

        String nombre = request.getParameter("nombre");
        String userName = request.getParameter("username");
        String email = request.getParameter("email");
        Date fechaNacimiento = ControlFechas.dateFromString(request.getParameter("fechaNacimiento"));
        Date fechaLimite = ControlFechas.dateFromString("2024-02-19");
        String password = request.getParameter("pwd");
        String repeat = request.getParameter("repeat");

        if(nombre.isBlank() || nombre.length() > 30){
            errores = true;
            mensajeError = mensajeError.concat("El nombre debe de ser entre 1 y 30 caracteres y no debe de estar vacio,");
        }

        if(userName.isBlank() || userName.length() > 30){
            errores = true;
            mensajeError = mensajeError.concat("El nombre de usuario debe de ser entre 1 y 30 caracteres y no debe de estar vacio,");
        }
        if(!Comprobadores.comprobarEmail(email)){
            mensajeError = mensajeError.concat("El email no es valido,");
            errores = true;
        }


        if(fechaNacimiento == null){
            mensajeError = mensajeError.concat("La fecha de nacimiento no puede estar vacia,");
            errores = true;
        }

        if (fechaNacimiento.after(fechaLimite)) {
            mensajeError = mensajeError.concat("La fecha de nacimiento no puede ser posterior a 19 de febrero de 2024,");
            errores = true;
        }

        boolean actualizarPwd = Boolean.parseBoolean(request.getParameter("actualizarPwd"));
        if (actualizarPwd) {
            if (!password.equals(repeat) || password.isBlank() || repeat.isBlank() || password.isEmpty() || repeat.isEmpty()) {
                mensajeError = mensajeError.concat("Las contraseñas no coinciden y no pueden estar vacias,");
                errores = true;
            }
        }

        if(errores){
            if(mensajeError.charAt(mensajeError.length() - 1) == ','){
                mensajeError = mensajeError.substring(0, mensajeError.length() - 1);
            }
            String mensajeCodificado = URLEncoder.encode(mensajeError, StandardCharsets.UTF_8);
            Cookie error = new Cookie("error", mensajeCodificado);
            error.setMaxAge(60);
            error.setPath("/");
            response.addCookie(error);
            response.sendRedirect(request.getContextPath() + "/pgs/AdminUsers.jsp");
        }else{

            Usuario usuarioActivo = (Usuario) request.getSession().getAttribute("usuarioActivo");
            Horoscopo horoscopo = horoscopoServicio.buscarHoroscopoPorFecha(fechaNacimiento);
            UsuarioUpdateDTO usuarioUpdateDTO = null;
            if(actualizarPwd){
                String safePwd = PasswordGenerator.generarContrasenaSegura(password);
                usuarioUpdateDTO = new UsuarioUpdateDTO(usuarioActivo.getId(),
                        nombre,userName,email,fechaNacimiento,safePwd,horoscopo);

            }else{
                usuarioUpdateDTO = new UsuarioUpdateDTO(usuarioActivo.getId(),
                        nombre,userName,email,fechaNacimiento,usuarioActivo.getPassword(),horoscopo);
            }
            usuarioServicio.actualizarUsuario(usuarioUpdateDTO);
            request.getSession().setAttribute("usuarioActivo", usuarioUpdateDTO.toModel() );
            String mensajeCodificado = URLEncoder.encode("Usuario actualizado correctamente", StandardCharsets.UTF_8);
            Cookie success = new Cookie("success", mensajeCodificado);
            success.setMaxAge(60);
            success.setPath("/");
            response.addCookie(success);
            response.sendRedirect(request.getContextPath() + "/pgs/AdminUsers.jsp");
        }

    }
}
