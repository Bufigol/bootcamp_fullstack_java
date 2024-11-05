<%@ page import="com.bufigol.modelo.Usuario" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Administración de Usuarios</title>
    <link rel="stylesheet" href="../utils/styles/styles.css">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+SC:wght@400;700&display=swap" rel="stylesheet">
    <script src="../utils/js/HabilitarPwdsFields.js"></script>
</head>
<body>
<%
    Usuario usuarioActivo = (Usuario) session.getAttribute("usuarioActivo");
    if (usuarioActivo == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
    String successMessage = null;
    String errorMessage = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("success")) {
                String mensajeCodificado = cookie.getValue();
                successMessage = URLDecoder.decode(mensajeCodificado, StandardCharsets.UTF_8);
                cookie.setMaxAge(-1);
                cookie.setPath("/");
                response.addCookie(cookie);
            } else if (cookie.getName().equals("error")) {
                String mensajeCodificado = cookie.getValue();
                errorMessage = URLDecoder.decode(mensajeCodificado, StandardCharsets.UTF_8);
                cookie.setMaxAge(-1);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
    }
%>
<% if (successMessage != null) { %>
<div class="message success" role="alert">
    <%= successMessage %>
</div>
<% } %>

<% if (errorMessage != null) { %>
<div class="message error" role="alert">
    <ul>
        <% for (String error : errorMessage.split(",")) { %>
        <li><%= error.trim() %></li>
        <% } %>
    </ul>
</div>
<% } %>
<div class="banner">
    <a href="./home.jsp">Home</a>
    <a href="${pageContext.request.contextPath}/pgs/InfoUsuario.jsp">Información del Usuario</a>
    <a href="${pageContext.request.contextPath}/pgs/AdminUsers.jsp">Administración de Usuarios</a>
    <a href="${pageContext.request.contextPath}/pgs/about.jsp">Sobre el Horóscopo Chino</a>
    <a href="${pageContext.request.contextPath}/procesar-logout">Log Out</a>
</div>
<div class="zodiac-icons">
    <span>🐀</span><span>🐂</span><span>🐅</span><span>🐇</span><span>🐉</span><span>🐍</span>
    <span>🐎</span><span>🐐</span><span>🐒</span><span>🐓</span><span>🐕</span><span>🐖</span>
</div>
<h1>Administración de Usuarios</h1>
<div class="info-container">
    <form id="userForm" action="${pageContext.request.contextPath}/ActualizarUsuario" method="post">
        <label for="username">Nombre de usuario:</label>
        <input id="username" name="username" type="text" value="<%= usuarioActivo.getUserName() %>">

        <label for="nombre">Nombre:</label>
        <input id="nombre" name="nombre" type="text" value="<%= usuarioActivo.getNombre() %>" required>

        <label for="email">Email:</label>
        <input id="email" name="email" type="email" value="<%= usuarioActivo.getEmail() %>" required>

        <label for="fechaNacimiento">Fecha de nacimiento:</label>
        <input type="date" id="fechaNacimiento" name="fechaNacimiento" value="<%= usuarioActivo.getFechaNacimiento() %>" required>

        <p>¿Actualizar contraseña?</p>
        <div class="radio-group">
            <input type="radio" id="si" name="actualizarPwd" value="true">
            <label for="si">Sí</label>
            <input type="radio" id="no" name="actualizarPwd" value="false">
            <label for="no">No</label>
        </div>

        <label for="pwd">Contraseña:</label>
        <input type="password" id="pwd" name="pwd">

        <label for="repeat">Repetir Contraseña:</label>
        <input type="password" id="repeat" name="repeat">

        <input type="submit" value="Actualizar">
    </form>
</div>
<div class="zodiac-icons">
    <span>🐀</span><span>🐂</span><span>🐅</span><span>🐇</span><span>🐉</span><span>🐍</span>
    <span>🐎</span><span>🐐</span><span>🐒</span><span>🐓</span><span>🐕</span><span>🐖</span>
</div>
</body>
</html>