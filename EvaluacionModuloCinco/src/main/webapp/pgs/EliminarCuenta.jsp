
<%@ page import="com.bufigol.modelo.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Eliminar Cuenta</title>
    <link rel="stylesheet" href="../utils/styles/styles.css">
</head>
<body>
<div class="banner">
    <a href="${pageContext.request.contextPath}/pgs/InfoUsuario.jsp">Información del Usuario</a>
    <a href="${pageContext.request.contextPath}/pgs/AdminUsers.jsp">Administración de Usuarios</a>
    <a href="${pageContext.request.contextPath}/pgs/about.jsp">Sobre el Horóscopo Chino</a>
    <a href="${pageContext.request.contextPath}/procesar-logout">Log Out</a>
</div>
<div class="zodiac-icons">
    <span>🐀</span><span>🐂</span><span>🐅</span><span>🐇</span><span>🐉</span><span>🐍</span>
    <span>🐎</span><span>🐐</span><span>🐒</span><span>🐓</span><span>🐕</span><span>🐖</span>
</div>

<h1>¿Está seguro que quiere eliminar su cuenta?</h1>
<form action="${pageContext.request.contextPath}/procesar-eliminar-cuenta" method="get">
    <div class="menu-options">
        <button class="menu-button" type="submit">SI</button>
        <button class="menu-button" onclick="location.href='${pageContext.request.contextPath}/pgs/home.jsp'">NO</button>
    </div>
</form>
<div class="zodiac-icons">
    <span>🐀</span><span>🐂</span><span>🐅</span><span>🐇</span><span>🐉</span><span>🐍</span>
    <span>🐎</span><span>🐐</span><span>🐒</span><span>🐓</span><span>🐕</span><span>🐖</span>
</div>
</body>
</html>
