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
        out.println("""
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Formulario ECM5S1</title>
                    <link rel="stylesheet" href="./styles/index.css" />
                </head>
                <html>
                <body>
        """);
        out.println("<h2> Respuesta del formulario:</h2>" +
                "</br>");
        out.println("<p>" + message + request.getParameter("nombre") + "</p>");
        out.println("</body></html>");

    }

    public void destroy() {
    }
}