package com.bufigol.ecm5s1;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "El nombre enviado es: ";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1> Respuesta del formulario:</h1>");
        out.println("<p>" + message + request.getParameter("nombre") + "</p>");
        out.println("</body></html>");

    }

    public void destroy() {
    }
}