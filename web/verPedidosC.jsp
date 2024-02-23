<%-- 
    Document   : verPedidosC
    Created on : 23-feb-2024, 11:47:32
    Author     : Julencia
--%>

<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PEDIDOS CLIENTE</title>
        
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
        <h1>PEDIDOS REALIZADOS</h1>
        <!-- FORMULARIO QUE CONTIENE EL SELECT CON LAS OPCIONES DE FILTRADO DE PEDIDOS DISPONIBLES -->
        <br><form action="ServerControlador" method="POST">

            <label for="opcion">Selecciona el estado del pedido:</label>
            <select name="opcion" id="opcion">
                <option value="procesado">Procesado</option>
                <option value="enviado">Enviado</option>
                <option value="reparto">En Reparto</option>
                <option value="entregado">Entregado</option>
                <option value="todos">Ver todos</option>
            </select>
            <input type="submit" value="Filtrar Pedido" name="enviar">
        </form><br><br>
        <%
            /*Obtenemos el resultset enviado desde el controlador, el cual tiene todos los pedidos sacados por la consulta*/
            ResultSet pedidosRec = (ResultSet) request.getAttribute("pedidos");

            /*Si este, es diferente de null es decir que tiene pedidos entra en el if y se imprime la tabla que muestra los pedidos,
            su estado etc*/
            if (pedidosRec != null)
            {%>
        <table>
            <tr>

                <th>ID PEDIDO</th>
                <th>ESTADO PEDIDO</th>
                <th>COSTE</th>
                <th>TOTAL ARTICULOS</th>

            </tr>
            <%
                /*Mediante el while recorremos todos los pedidos y con el metodo . next vamos obteniendo el siguiente registro e imprimiendolo
                en la tabla correspondiente*/
                while (pedidosRec.next())
                {
            %>
            <tr>
                <td><%=pedidosRec.getInt("id_pedido")%></td>
                <td><%=pedidosRec.getString("Estado")%></td>
                <td><%=pedidosRec.getDouble("TOTAL")%></td>
                <td><%=pedidosRec.getInt("Articulos")%></td>
            </tr>

            <%
                }
            %>
        </table>
        <%
            }
        %>
        
            <br><hr><hr>
            <a href="menu.jsp">Volver al Menu</a>
            <hr><hr>
    </body>
</html>
