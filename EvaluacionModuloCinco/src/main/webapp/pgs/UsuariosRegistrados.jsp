<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bufigol.dtos.usuario.UsuarioResponseDTO" %>
<%@ page import="com.bufigol.modelo.Usuario" %>
<%@ page import="com.bufigol.servicios.UsuarioServicio" %>
<html>
<head>
  <title>Listado de Usuarios</title>
  <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+SC:wght@400;700&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="../utils/styles/styles.css">
  <link rel="stylesheet" href="../utils/styles/table-styles.css">
</head>
<body>
<%
  Usuario usuarioActivo = (Usuario) session.getAttribute("usuarioActivo");
  if (usuarioActivo == null) {
    response.sendRedirect(request.getContextPath() + "/index.jsp");
    return;
  } else {
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
  <h1 class="text-3xl font-bold mb-4">Listado de usuarios registrados</h1>

    <table class="table-auto w-full">
      <thead>
      <tr>
        <th class="px-4 py-2">ID</th>
        <th class="px-4 py-2">Nombre</th>
        <th class="px-4 py-2">Username</th>
        <th class="px-4 py-2">Email</th>
        <th class="px-4 py-2">Fecha de Nacimiento</th>
        <th class="px-4 py-2">Horóscopo</th>
      </tr>
      </thead>
      <tbody>
      <%
        UsuarioServicio usrServ = new UsuarioServicio();
        List<UsuarioResponseDTO> usuarios = usrServ.listarUsuarios();
        if (usuarios != null) {
          for (UsuarioResponseDTO usuario : usuarios) {
      %>
      <tr>
        <td class="border px-4 py-2"><%= usuario.getId() %></td>
        <td class="border px-4 py-2"><%= usuario.getNombre() %></td>
        <td class="border px-4 py-2"><%= usuario.getUserName() %></td>
        <td class="border px-4 py-2"><%= usuario.getEmail() %></td>
        <td class="border px-4 py-2"><%= usuario.getFechaNacimiento() %></td>
        <td class="border px-4 py-2"><%= usuario.getHoroscopo().getAnimal() %></td>
      </tr>
      <%
        }
      } else {
      %>
      <tr>
        <td colspan="6" class="border px-4 py-2 text-center">No se encontraron usuarios registrados.</td>
      </tr>
      <%
        }
      %>
      </tbody>
    </table>


  <div class="zodiac-icons">
    <span>🐀</span><span>🐂</span><span>🐅</span><span>🐇</span><span>🐉</span><span>🐍</span>
    <span>🐎</span><span>🐐</span><span>🐒</span><span>🐓</span><span>🐕</span><span>🐖</span>
  </div>

</body>
</html>