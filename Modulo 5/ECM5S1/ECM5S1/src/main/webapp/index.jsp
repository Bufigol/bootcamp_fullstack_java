<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulario ECM5S1</title>
    <link rel="stylesheet" href="./styles/index.css" />
</head>
<body>
<form action="/hello-servlet" method="GET">
    <h2>Formulario</h2>
    <label for="nombre">Nombre:</label>
    <input type="text" id="nombre" name="nombre" required placeholder="Ingresa tu nombre">
    <input type="submit" value="Enviar">
</form>
</body>
</html>