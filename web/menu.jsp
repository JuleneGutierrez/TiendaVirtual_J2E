
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menú</title>
        <style>
            * {
                font-family: Helvetica, Verdana, sans-serif;
                text-align: center;
            }
            h2 {
                color: darkred;
            }

            input {
                border-radius: 4px;
            }

            input:hover {
                background-color: palevioletred;
            }
        </style>
    </head>
    <body>


        <%if (session.getAttribute("usuario") != null) {%>
        <h1>MENÚ</h1>


        <h2>Bienvenido, <%=session.getAttribute("nombre")%>!</h2>

        <hr> 
        <h3>¿Qué quieres hacer?</h3>

        <form method="POST" action="ServerControlador">
            <% if ("vendedor".equals(session.getAttribute("rol"))) { %>
            <label for="solicitudes">Comprueba las solicitudes de tus clientes</label><br><br>
            <input type="submit" value="verSolicitudes" name="enviar" /><br><br>

            <label for="estados">Cambia los estados de los pedidos</label><br><br>
            <input type="submit" value="Cambiar Estados" name="enviar" /><br><br>

            <% } else if ("comprador".equals(session.getAttribute("rol"))) { %>

            <label for="solicitudes">Accede al catálogo y compra</label><br><br>
            <input type="submit" value="Comprar" name="enviar" /><br><br>

            <label for="solicitudes">Comprueba el estado de tus pedidos</label><br><br>
            <input type="submit" value="EstadoPedido" name="enviar" /><br><br>
        </form>

        <hr>
        <form method="POST" action="ServerControlador">
            <label for="salida">¿Ya terminaste?</label><br>
            <input type="submit" name="enviar" value="Salir" />

        </form><br>

        <% }%>
        <% } else {%> <h3>ACCESO NO PERMITIDO</h3> <%}%>
    </body>
</html>
