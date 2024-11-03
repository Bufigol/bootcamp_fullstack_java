<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro - Horóscopo Chino</title>
    <link rel="stylesheet" href="../utils/styles/index.css">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+SC:wght@400;700&display=swap" rel="stylesheet">
    <script src="../utils/js/comprobacionesRegistro.js"></script>

</head>
<body>
<div class="banner">
    <a href="${pageContext.request.contextPath}/index.jsp">Inicio de Sesión</a>
    <a href="${pageContext.request.contextPath}/pgs/register.jsp">Registrarse</a>
    <a href="">Sobre el Horóscopo Chino</a>
</div>

<div class="content-wrapper">
    <div class="form-container">
        <h2>Registro de Usuario</h2>

        <!-- Sección para mostrar errores -->
        <%
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("errores")) {
                        String[] errores = cookie.getValue().split(",");
        %>
        <div class="errores-container">
            <% for (String error : errores) { %>
            <div class="error-mensaje"><%= error %></div>
            <% } %>
        </div>
        <%
                        cookie.setMaxAge(0);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                        break;
                    }
                }
            }
        %>

        <form action="${pageContext.request.contextPath}/procesar-registro" method="GET">
            <div class="form-group">
                <label for="userName">Nombre de Usuario:</label>
                <input type="text" id="userName" name="userName" required
                       maxlength="30" pattern="[A-Za-z0-9]+"
                       title="Solo letras y números, máximo 30 caracteres">
            </div>

            <div class="form-group">
                <label for="nombre">Nombre:</label>
                <input type="text" id="nombre" name="nombre" required
                       maxlength="30" pattern="[A-Za-zÁáÉéÍíÓóÚúÑñ\s]+"
                       title="Solo letras, máximo 30 caracteres">
            </div>

            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>

            <div class="form-group">
                <label for="fechaNacimiento">Fecha de Nacimiento:</label>
                <input type="date" id="fechaNacimiento" name="fechaNacimiento" required>
            </div>

            <div class="form-group">
                <label for="password">Contraseña:</label>
                <input type="password" id="password" name="password" required
                       minlength="8">
            </div>

            <div class="form-group">
                <label for="confirmarPassword">Confirmar Contraseña:</label>
                <input type="password" id="confirmarPassword" name="confirmarPassword"
                       required minlength="8">
            </div>

            <button type="submit">Registrarse</button>
        </form>
        <div class="zodiac">
            <span>🐀</span><span>🐂</span><span>🐅</span><span>🐇</span><span>🐉</span><span>🐍</span>
            <span>🐎</span><span>🐐</span><span>🐒</span><span>🐓</span><span>🐕</span><span>🐖</span>
        </div>
    </div>

</div>
</body>
</html>