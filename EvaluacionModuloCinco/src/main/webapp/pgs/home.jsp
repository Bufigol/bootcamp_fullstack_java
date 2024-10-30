<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Información de Usuario</title>
  <link rel="stylesheet" href="../utils/styles/home.css">
</head>
<body>
<div class="container">
  <h1>Información de Usuario</h1>

  <%
    String usuarioID = null;
    String usrName = null;
    Cookie[] cookies = request.getCookies();

    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("usuarioID")) {
          usuarioID = cookie.getValue();
        }
        if (cookie.getName().equals("usrName")) {
          usrName = cookie.getValue();
        }
      }
    }
  %>

  <% if (usuarioID != null) { %>
  <div class="info">
    <strong>ID de Usuario:</strong> <%= usuarioID %>
  </div>
  <% } else { %>
  <div class="no-cookie">
    No se encontró la cookie 'usuarioID'.
  </div>
  <% } %>

  <% if (usrName != null) { %>
  <div class="info">
    <strong>Nombre de Usuario:</strong> <%= usrName %>
  </div>
  <% } else { %>
  <div class="no-cookie">
    No se encontró la cookie 'usrName'.
  </div>
  <% } %>
</div>
</body>
</html>