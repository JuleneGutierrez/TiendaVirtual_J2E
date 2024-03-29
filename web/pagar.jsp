<%@page import="modelo.Cesta"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Zona de Pago</title>
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

            .total {
                font-weight: bold;
                background-color: lime;
            }


        </style>

    </head>
    <body>
        <%if (session.getAttribute("rol") != null && session.getAttribute("rol").equals("comprador")) {%>
        <%
            /* Obtenemos en la variable cestaGuarda, la cesta con los productos seleccionados por el usuarios para comprar, de la variable
            de sesion cesta. Es necesario castearlo porque en si java no sabe que tipo de objeto contiene ese variable de sesion*/
            Cesta cestaGuardada = (Cesta) session.getAttribute("cesta");
            
            /* variable de tipo double donde vamos a guardar el sumatorio final de precio total de todos los productos */
            double totalSumatorio = 0;
        %>
        <form name="factura" action="ServerControlador" method="POST">
            <h1>Zona de pago</h1>
            <hr>
            <table>
                <tr>
                    <th>Producto</th>
                    <th>Precio Unitario</th>
                    <th>Cantidad</th>
                    <th>Total/producto</th>
                </tr>

                <%
                    /*Usamos un bucle for para recorrer el array de productos de la cesta del usuario */
                    for (int i = 0; i < cestaGuardada.getArrayProductos().size(); i++)
                    {
                        /*Aqui guardamos el total del precio basada en la cantidad ejemplo: 2 libros de alas de sangre en total seran 20, 
                        el precio unitario es de 10 para calcularlo multiplicamos el precio del producto por la cantidad de ejemplares 
                        añadidos*/
                        double totalProducto = cestaGuardada.getArrayProductos().get(i).getPrecio() * cestaGuardada.getArrayProductos().get(i).getCantidad();
                        
                        /*En la variable creada antes del form vamos sumando lo que hay mas la suma anterior, esto nos da el total final*/
                        totalSumatorio = totalSumatorio + totalProducto;
                %>

                <tr>
                    <td><%=cestaGuardada.getArrayProductos().get(i).getTitulo()%></td>
                    <td><%=String.format("%.2f", cestaGuardada.getArrayProductos().get(i).getPrecio())%> €</td>
                    <td><%=cestaGuardada.getArrayProductos().get(i).getCantidad()%></td>
                    <td><%= String.format("%.2f", totalProducto) %> €</td>

                </tr>


                <%
                    }

                    /* Guardamos en una variable de sesion el total de la factura para despues usarlo para insertarlo en la tabla de 
                       pedidos */
                    session.setAttribute("sumatorio", totalSumatorio);
                %>
                <tr>  <td colspan='3'>TOTAL:</td> <td class='total'><%=String.format("%.2f", totalSumatorio)%> €</td> </tr>
            </table>
            
            <br>

            <input type="submit" name="enviar" value="Pagar">
        </form>
        <br><br>

        <br><hr>
        <form method="POST" action="ServerControlador">
            <label for="vueltaCatalogo">¿Quieres cambiar algo?</label><br>
            <input type="submit" name="enviar" value="Volver al Catalogo" />

        </form><br>
        <% } else {%> <h3>ACCESO NO PERMITIDO</h3> <%}%>
    </body>
</html>
