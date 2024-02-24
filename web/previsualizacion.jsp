

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            * {
                font-family: Helvetica, Verdana, sans-serif;
                text-align: center;
            }

            input {
                border-radius: 4px;
            }

            input:hover {
                background-color: lightskyblue;
            }

            table{
                margin-left: auto;
                margin-right: auto;
                width: 80%;

            }

            tr
            {
                height: 40px
            }

            th {
                background-color: lightskyblue;
            }

            td {
                padding-left: 20px;
                text-align: left;
            }

            table, tr, td,th
            {
                border: 2px solid;
                border-collapse: collapse;
            }

            input {
                margin-top: 15px;
            }
        </style>
    </head>
    <body>
        <h1>ESTAS EN PREVISUALIZACION</h1>

        <hr>

        <br>
        <form method="POST" action="ServerControlador">
            <label for="solicitudes">¿Te gustaría comprar? Solicita el cambio</label><br>
            <input type="submit" name="enviar" value="Cambiar Rol" />
        </form><br>
        <!-- Formulario con un boton para salir, al pulsarlo se redirige al login y se cierra la sesion(Gestionado en el login el cierre de sesion) -->
        <hr>
        <form method="POST" action="ServerControlador">

            <input type="submit" name="enviar" value="Salir" />

        </form><br>
    </body>
</html>
