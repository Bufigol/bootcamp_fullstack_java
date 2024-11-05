<%@ page import="com.bufigol.modelo.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Información de Usuario</title>
  <link rel="stylesheet" href="../utils/styles/styles.css">
</head>
<body>
<%
  Usuario usuarioActivo = (Usuario) session.getAttribute("usuarioActivo");
  if (usuarioActivo == null) {
    response.sendRedirect(request.getContextPath() + "/index.jsp");
    return;
  }else{
    session.setAttribute("usuarioActivo", usuarioActivo);
  }
%>
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
  <%
    Cookie[] cookies = request.getCookies();
    String username = "";

    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("usrName")) {
                username = cookie.getValue();
            }

        }
    }
    if(username.isEmpty()) {
        username = usuarioActivo.getUserName();
    }
  %>
  <h1>¿Que deseas hacer, <%= username %> ?</h1>
  <div class="menu-options">
    <button class="menu-button" onclick="location.href='${pageContext.request.contextPath}/pgs/InfoUsuario.jsp'">Conoce tu animal</button>
    <button class="menu-button" onclick="location.href='/pgs/animal.jsp'">Buscar usuarios</button>
    <button class="menu-button" onclick="location.href='/pgs/AdminUsers.jsp'">Modificar datos</button>
    <button class="menu-button" onclick="location.href='/pgs/EliminarCuenta.jsp'">Eliminar cuenta</button>
  </div>
  <div class="zodiac-icons">
    <span>🐀</span><span>🐂</span><span>🐅</span><span>🐇</span><span>🐉</span><span>🐍</span>
    <span>🐎</span><span>🐐</span><span>🐒</span><span>🐓</span><span>🐕</span><span>🐖</span>
  </div>
</body>
</html>