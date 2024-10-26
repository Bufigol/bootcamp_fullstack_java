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
    <div id="login" class="content active">
        <form action="/login" method="get">
            <input type="text" name="login_username" placeholder="Nombre de usuario" required>
            <input type="password" name="login_password" id ="password" placeholder="Contraseña" required>
            <button type="submit">Iniciar Sesión</button>
        </form>
    </div>
    <div id="register" class="content">
        <form action="/register" method="post">
            <input type="text" name="username" placeholder="Nombre de usuario" required>
            <input type="password" name="password" placeholder="Contraseña" required>
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

        document.querySelector(`.tab:nth-child(${tabName == 'login' ? 1 : 2})`).classList.add('active');
        document.getElementById(tabName).classList.add('active');
    }
</script>
</body>
</html>
