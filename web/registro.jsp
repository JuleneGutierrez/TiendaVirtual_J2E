
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registro</title>
        <style>
            * {
                font-family: Helvetica, Verdana, sans-serif;
                text-align: center;
            }

            input {
                border-radius: 4px;
            }

            input:hover {
                background-color: palevioletred;
            }

            h1 {
                color: darkred;
            }


        </style>
    </head>
    <body>
        <%
            String errorRegistro = (String) session.getAttribute("registroError");
            if (errorRegistro != null) {

                out.print(errorRegistro);
            }

        %>
        <form method="POST" action="ServerControlador">

            <h1>Nuevo Registro</h1>
            <hr>
            <h3>Introduce tus datos</h3>

            Nombre:
            <br>
            <input type="text" name="nombre" value="" />
            <br><br>

            Apellido:
            <br>
            <input type="text" name="apellido" value="" />
            <br><br>

            Nombre usuario (login):
            <br>
            <input type="text" name="usuario" value="" />
            <br><br>

            Contraseña:
            <br><br>
            <input type="password" name="contrasena" value="" />
            <br><br>

            Email:<br>
            <input type="text" name="email" value="" placeholder="example@example.com" /><br><br>


            Telefono:<br>
            <input type="tel" id="telefono" name="telefono" pattern="[6789]\d{8}" placeholder="Ej. 612345678" required>
            <br><br>

            DNI:<br>
            <input type="text" id="dni" name="dni">
            <br><br>

            <input type="submit" value="Confirmar" name="enviar" /><br><br>
        </form>
        <form method="POST" action="ServerControlador">
            <hr>
            <p>¿No te quieres registrar, o ya lo hiciste con éxito?</p>
            <input type="submit" value="Volver al login" name="enviar" />
        </form>
    </body>
</html>
