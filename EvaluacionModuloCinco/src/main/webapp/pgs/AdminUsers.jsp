<%@ page import="com.bufigol.modelo.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Administración de Usuarios</title>
    <link rel="stylesheet" href="../utils/styles/AdminUsers.css">
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
%>
<div class="banner">
    <a href="${pageContext.request.contextPath}/Informacion_del_Usuario">Información del Usuario</a>
    <a href="${pageContext.request.contextPath}/pgs/AdminUsers.jsp">Administración de Usuarios</a>
    <a href="${pageContext.request.contextPath}/pgs/about.jsp">Sobre el Horóscopo Chino</a>
    <a href="${pageContext.request.contextPath}/ProcesarLogout">Log Out</a>
</div>
<div class="zodiac-icons">
    <span>🐀</span><span>🐂</span><span>🐅</span><span>🐇</span><span>🐉</span><span>🐍</span>
    <span>🐎</span><span>🐐</span><span>🐒</span><span>🐓</span><span>🐕</span><span>🐖</span>
</div>
<h1>Administración de Usuarios</h1>
<div class="info-container">
    <form id="userForm" action="${pageContext.request.contextPath}/ActualizarUsuario" method="post">
        <label for="username">Nombre de usuario:</label>
        <input id="username" name="username" type="text" value="<%= usuarioActivo.getUserName() %>" disabled readonly>

        <label for="nombre">Nombre:</label>
        <input id="nombre" name="nombre" type="text" value="<%= usuarioActivo.getNombre() %>" required>

        <label for="email">Email:</label>
        <input id="email" name="email" type="email" value="<%= usuarioActivo.getEmail() %>" required>

        <label for="fechaNacimiento">Fecha de nacimiento:</label>
        <input type="date" id="fechaNacimiento" name="fechaNacimiento" value="<%= usuarioActivo.getFechaNacimiento() %>" required>

        <p>¿Actualizar contraseña?</p>
        <div class="radio-group">
            <input type="radio" id="si" name="actualizarPwd" value="si">
            <label for="si">Sí</label>
            <input type="radio" id="no" name="actualizarPwd" value="no">
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