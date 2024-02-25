<%@page import="java.util.ArrayList"%>
<%@page import="modelo.Solicitud"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gestión</title>
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

            .checkbox {
                text-align: center;
            }

            #input {

                width: 40px;
                height: 15px;
                margin-left: 10px;
            }


        </style>
    </head>
    <body>
        <h1>ESTAS EN CATALOGO</h1>
        <%
            ArrayList<Solicitud> solicitudes = (ArrayList<Solicitud>) session.getAttribute("solicitudes");
            if (!solicitudes.isEmpty()) {
        %>          
        <form method="POST" action="ServerControlador">
            <table>
                <tr>
                    <th>ID solicitud</th>
                    <th>ID usuario</th>
                    <th>Estado</th>
                    <th>Fecha</th>
                    <th>Cambiar rol</th>
                </tr>
                <%
                    /*Recorremos el tamaño del arraylist que contiene todos las solicitudes pendientes que se encuentran en la base de datos*/
                    for (int i = 0; i < solicitudes.size(); i++) {
                %>     
                <tr>
                    <td><%=solicitudes.get(i).getIdSolicitud()%></td>
                    <td><%=solicitudes.get(i).getIdUsuario()%></td>
                    <td><%=solicitudes.get(i).getEstado()%></td>
                    <td><%=solicitudes.get(i).getFechaSolicitud()%></td>
                    <td><input type='checkbox' name='opciones' value='<%=solicitudes.get(i).getIdUsuario()%>'></td>
                </tr>

                <%

                    }
                %> 
            </table>
            </br>
            <input type="submit" value="Cambiar de Rol" name="enviar" />
        </form>
        <%
            } else {
                out.print("No hay solicitutes disponibles");
            }

        %>  
        <br><hr>
        <form method="POST" action="ServerControlador">
            <label for="vueltaMenu">¿Qué más necesitas hacer?</label><br>
            <input type="submit" name="enviar" value="Volver al menu" />

        </form><br>




    </body>
</html>
