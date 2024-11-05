package com.bufigol.servlets;

import com.bufigol.dtos.usuario.UsuarioResponseDTO;
import com.bufigol.modelo.Usuario;
import com.bufigol.servicios.HoroscopoServicio;
import com.bufigol.servicios.UsuarioServicio;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;



public class ObtenerInfoZodiaco extends HttpServlet {
    private UsuarioServicio usuarioServicio;
    private HoroscopoServicio horoscopoServicio;
    private Usuario usuarioActivo;

    @Override
    public void init() {
        this.usuarioServicio = new UsuarioServicio();
        this.horoscopoServicio = new HoroscopoServicio();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Cookie[] cookies = request.getCookies();
            String username = "";
            int usuarioID = 0;

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("usrName")) {
                        username = cookie.getValue();
                    }
                    if (cookie.getName().equals("usuarioID")) {
                        usuarioID = Integer.parseInt(cookie.getValue());
                    }
                }

                if (!username.isEmpty() && usuarioID != 0) {
                    UsuarioResponseDTO usuarioUsrName = usuarioServicio.buscarUsuarioPorUserName(username);
                    Optional<UsuarioResponseDTO> usrID = usuarioServicio.buscarUsuarioPorId(usuarioID);

                    if (usrID.isPresent() && usuarioUsrName != null) {
                        Usuario user1 = usuarioUsrName.toModel();
                        Usuario user2 = usrID.get().toModel();

                        if (user1.equals(user2)) {
                            this.usuarioActivo = user1;

                            // Establecer atributos de sesión
                            request.getSession().setAttribute("usuarioActivo", this.usuarioActivo);

                            // Establecer cookie de zodiaco
                            Cookie cookieZodiaco = new Cookie("zodiaco", this.usuarioActivo.getHoroscopo().getAnimal());
                            cookieZodiaco.setMaxAge(15);
                            cookieZodiaco.setPath("/");
                            response.addCookie(cookieZodiaco);

                            // Usar sendRedirect con la ruta completa
                            response.sendRedirect(request.getContextPath() + "/pgs/InfoUsuario.jsp");
                            return;
                        }
                    }
                }
            }

            // Si algo falla, redirigir al index
            response.sendRedirect(request.getContextPath() + "/index.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            Cookie error = new Cookie("errorCookies", "true");
            error.setMaxAge(60);
            error.setPath("/");
            response.addCookie(error);
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } else {
            doGet(request, response);
        }
    }
}