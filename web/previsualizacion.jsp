
<%@page import="java.util.ArrayList"%>
<%@page import="modelo.Producto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Previsualización</title>
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

            h1, h2 {
                color: darkred;
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
                background-color: palevioletred;
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
        <%
            String mensaje = (String) session.getAttribute("mensajeCambiarRolInvitado");
            if (mensaje != null) {

                out.print(mensaje);
            }

        %>
        <hr>
        <h2>Títulos Disponibles en Catálogo</h2>
        <table>
            <tr>
                <th>Titulo</th>
                <th>Autor</th>
                <th>Precio</th>
                <th>Editorial</th>
            </tr>
            <%                /*Creamos un arraylist para guardar libros de la BD*/
                ArrayList<Producto> libros = (ArrayList<Producto>) session.getAttribute("libros");

                /*Recorremos el tamaño del arraylist que contiene todos los libros que se encuentran en la base de datos*/
                for (int i = 0; i < libros.size(); i++) {
            %>

            <tr>
                <td><%=libros.get(i).getTitulo()%></td>
                <td><%=libros.get(i).getAutor()%></td>
                <td><%=String.format("%.2f", libros.get(i).getPrecio())%> €</td>
                <td><%=libros.get(i).getEditorial()%></td>
            </tr>


            <% }%>

        </table>

        <hr>
        <br>

        <form method="POST" action="ServerControlador">
            <label for="solicitudes">¿Te gustaría comprar? Solicita el cambio</label><br>
            <input type="submit" name="enviar" value="Cambiar Rol" />
        </form><br>
        <!-- Formulario con un boton para salir, al pulsarlo se redirige al login y se cierra la sesion(Gestionado en el login el cierre de sesion) -->
        <hr>
        <form method="POST" action="ServerControlador">
            <label for="salida">¿Ya terminaste?</label><br>
            <input type="submit" name="enviar" value="Salir" />

        </form><br>
    </body>
</html>
