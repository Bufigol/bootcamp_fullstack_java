<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./utils/styles/styles.css">
    <title>Horóscopo Chino - Inicio de Sesión</title>
</head>
<body>
<%
    String usuarioID = null;
    String usrName = "";
    Cookie[] cookies = request.getCookies();
    String cuentaEliminada = null;
    if (cookies != null) {
        for (Cookie cookie : cookies) {

            if (cookie.getName().equals("usrName")) {
                usrName = cookie.getValue();
            }
            if(cookie.getName().equals("cuentaEliminada")) {
                cuentaEliminada = "Cuenta eliminada con exito";
            }
        }
    }
%>
<div class="banner">
    <a href="${pageContext.request.contextPath}/pgs/register.jsp">Registrarse</a>
    <a href="${pageContext.request.contextPath}/pgs/about.jsp">Sobre el Horóscopo Chino</a>
</div>
<div class="container">
    <h1>Horóscopo Chino</h1>
    <% if (cuentaEliminada != null) { %>
    <div class="message success" role="alert">
        <%= cuentaEliminada %>
    </div>
    <% } %>
    <p style="text-align: center;">Inicia sesión para descubrir tu destino</p>
    <form action="/login" method="POST">
        <label for="username" >Nombre de usuario:</label>
        <input type="text" id="username" name="username"  value="<%= usrName %>" required >

        <label for="password">Contraseña:</label>
        <input type="password" id="password" name="password" required>

        <button type="submit">Iniciar Sesión</button>
    </form>
    <div class="zodiac">
        <span>🐀</span><span>🐂</span><span>🐅</span><span>🐇</span><span>🐉</span><span>🐍</span>
        <span>🐎</span><span>🐐</span><span>🐒</span><span>🐓</span><span>🐕</span><span>🐖</span>
    </div>
</div>
</body>
</html>