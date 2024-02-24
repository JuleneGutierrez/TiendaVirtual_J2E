<%@page import="java.util.ArrayList"%>
<%@page import="modelo.Pedido"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cambiar estados de los pedidos</title>
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
        <h1>Cambiar estados de los pedidos</h1><hr>

        <form method="POST" action="ServerControlador">

            <label for="opcion">Filtrar por estado:</label>
            <select name="opcion" id="opcion">
                <option value="procesado">Procesado</option>
                <option value="enviado">Enviado</option>
                <option value="reparto">En Reparto</option>
                <option value="entregado">Entregado</option>
                <option value="todos">Ver todos</option>
            </select>
            <input type="submit" value="Filtrar estado" name="enviar"/>

        </form>


        <br><br>

        <form method="POST" action="ServerControlador">
            <table>
                <tr>
                    <th>ID USUARIO</th>
                    <th>ID PEDIDO</th>
                    <th>ESTADO PEDIDO</th>
                    <th>COSTE</th>
                    <th>TOTAL ARTICULOS</th>
                    <th></th>
                </tr>

                <%
                    /*Creamos un arraylist para guardar los estados de los pedidos de la BD*/
                    ArrayList<Pedido> estadoPedidos = (ArrayList<Pedido>) session.getAttribute("estadoPedidos");
                    //
                    /*Recorremos el tamaño del arraylist que contiene todos los libros que se encuentran en la base de datos*/
                    for (int i = 0; i < estadoPedidos.size(); i++) {
                %>
                <tr>
                    <td><%=estadoPedidos.get(i).getIdUsuario()%></td>
                    <td><%=estadoPedidos.get(i).getIdPedido()%></td>
                    <td><%=estadoPedidos.get(i).getEstadoPedido()%></td>
                    <td><%=estadoPedidos.get(i).getFacturado()%></td>
                    <td><%=estadoPedidos.get(i).getDetallePedido().getCantidad()%></td>

                    <td><input type='checkbox' name='opciones' value='<%=estadoPedidos.get(i).getIdPedido()%>'></td>
                </tr>
                <%
                    }
                %>
            </table>

            <br><br><br>


            <label for="rol">Cambiar a:</label>
            <select name="rol" id="rol">
                <option value="procesado">Procesado</option>
                <option value="enviado">Enviado</option>
                <option value="reparto">En Reparto</option>
                <option value="entregado">Entregado</option>
            </select>
            <input type="submit" name="enviar" value="Modificar Rol"> 
        </form>
    </body>
</html>
