<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./utils/styles/index.css">
    <title>EVM5S1</title>

</head>
<body>
<div class="container">
    <div class="tabs">
        <div class="tab active" onclick="showTab('login')">Iniciar Sesión</div>
        <div class="tab" onclick="showTab('register')">Registrarse</div>
    </div>

    <%-- Manejo de mensajes --%>
    <%
        String error = request.getParameter("error");
        String registered = request.getParameter("registered");
        String tab = request.getParameter("tab");

        if (error != null) {
    %>
    <div class="message error-message">
        <%
            switch(error) {
                case "empty":
                    out.println("Por favor, complete todos los campos");
                    break;
                case "invalid":
                    out.println("Usuario o contraseña incorrectos");
                    break;
                case "mismatch":
                    out.println("Las contraseñas no coinciden");
                    break;
                case "exists":
                    out.println("El nombre de usuario ya está en uso");
                    break;
                default:
                    out.println("Ha ocurrido un error");
            }
        %>
    </div>
    <%
        }

        if (registered != null && registered.equals("true")) {
    %>
    <div class="message success-message">
        ¡Registro exitoso! Ya puedes iniciar sesión
    </div>
    <%
        }
    %>

    <div id="login" class="content active">
        <form action="/login" method="get">
            <div class="form-group">
                <input type="text"
                       name="login_username"
                       placeholder="Nombre de usuario"
                       required
                       autocomplete="username">
            </div>
            <div class="form-group">
                <input type="password"
                       name="login_password"
                       placeholder="Contraseña"
                       required
                       autocomplete="current-password">
            </div>
            <button type="submit">Iniciar Sesión</button>
        </form>
    </div>

    <div id="register" class="content">
        <form action="/registro" method="get">
            <div class="form-group">
                <input type="text"
                       name="username"
                       placeholder="Nombre de usuario"
                       required
                       autocomplete="username">
            </div>
            <div class="form-group">
                <input type="password"
                       name="password"
                       placeholder="Contraseña"
                       required
                       autocomplete="new-password">
            </div>
            <div class="form-group">
                <input type="password"
                       name="password_conmfirm"
                       placeholder="Confirmar contraseña"
                       required
                       autocomplete="new-password">
            </div>
            <button type="submit">Registrarse</button>
        </form>
    </div>
</div>

<script>
    function showTab(tabName) {
        const tabs = document.querySelectorAll('.tab');
        const contents = document.querySelectorAll('.content');

        tabs.forEach(tab => tab.classList.remove('active'));
        contents.forEach(content => content.classList.remove('active'));

        document.querySelector(`.tab:nth-child(${tabName === 'login' ? 1 : 2})`).classList.add('active');
        document.getElementById(tabName).classList.add('active');
    }

    // Mantener la pestaña activa después de un error
    <% if (tab != null) { %>
    window.onload = function() {
        showTab('<%= tab %>');
    }
    <% } %>

    // Remover mensajes después de 5 segundos
    document.addEventListener('DOMContentLoaded', function() {
        const messages = document.querySelectorAll('.message');
        messages.forEach(message => {
            setTimeout(() => {
                message.style.opacity = '0';
                message.style.transition = 'opacity 0.5s ease';
                setTimeout(() => message.remove(), 500);
            }, 5000);
        });
    });
</script>
</body>
</html>