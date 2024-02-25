

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
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

        <h1>Iniciar Sesion</h1>
        <hr>
        <h3>Accede y encuentra lo que buscas</h3>
        <%
            String login = (String) session.getAttribute("loginMensaje");
            if (login != null) {

                out.print(login);
            }

        %>
        <br>
        <form method="POST" action="ServerControlador">
            Usuario:<br> 
            <input type="text" name="usuario" value=""/><br><br>
            Contraseña:<br>
            <input type="password" name="contrasena" value=""/><br><br>

            <input type="submit" value="Entrar" name="enviar"/>
            <br>
            <hr>
            <p>¿No tienes una cuenta? Registrate aquí</p>
            <input type="submit" value="Registrarse" name="enviar"/>
        </form>

    </body>
</html>
