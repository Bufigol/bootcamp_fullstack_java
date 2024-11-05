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
  }
%>
<div class="banner">
  <a href="${pageContext.request.contextPath}/pgs/home.jsp">Home</a>
  <a href="${pageContext.request.contextPath}/pgs/AdminUsers.jsp">Administración de Usuarios</a>
  <a href="${pageContext.request.contextPath}/pgs/about.jsp">Sobre el Horóscopo Chino</a>
  <a href="${pageContext.request.contextPath}/procesar-logout">Log Out</a>
</div>
<div class="container">
  <div class="zodiac-icons">
    <span>🐀</span><span>🐂</span><span>🐅</span><span>🐇</span><span>🐉</span><span>🐍</span>
    <span>🐎</span><span>🐐</span><span>🐒</span><span>🐓</span><span>🐕</span><span>🐖</span>
  </div>
  <h1>Conoce tu animal del hóroscopo chino</h1>
  <div class="info-container">
    <div class="text-content">
      <p>Hola <%= usuarioActivo.getNombre() %>, tu animal del hóroscopo chino es: <%= usuarioActivo.getHoroscopo().getAnimal() %></p>
      <p>Tu elemento de la suerte es: <%= usuarioActivo.getHoroscopo().getElemento() %></p>
      <p>Se caracteriza por ser: <%= usuarioActivo.getHoroscopo().getAnimalEnum().getCaracteristicas() %></p>
    </div>
    <div class="image-content">
      <img src="<%= usuarioActivo.getHoroscopo().getAnimalEnum().getRutaImagen() %>" alt="<%= usuarioActivo.getHoroscopo().getAnimal() %>" />
    </div>
  </div>
  <div class="zodiac-icons">
    <span>🐀</span><span>🐂</span><span>🐅</span><span>🐇</span><span>🐉</span><span>🐍</span>
    <span>🐎</span><span>🐐</span><span>🐒</span><span>🐓</span><span>🐕</span><span>🐖</span>
  </div>
</div>
</body>
</html>