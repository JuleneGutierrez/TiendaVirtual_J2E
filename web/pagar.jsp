<%@page import="modelo.Cesta"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <meta charset="UTF-8">
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

            .total {
                font-weight: bold;
                background-color: lime;
            }


        </style>

    </head>
    <body>
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
                    <td><%=cestaGuardada.getArrayProductos().get(i).getPrecio()%></td>
                    <td><%=cestaGuardada.getArrayProductos().get(i).getCantidad()%></td>
                    <td><%=totalProducto%></td>
                </tr>


                <%
                    }

                    /* Guardamos en una variable de sesion el total de la factura para despues usarlo para insertarlo en la tabla de 
                       pedidos */
                    session.setAttribute("sumatorio", totalSumatorio);
                %>
                <tr>  <td colspan='3'>TOTAL:</td> <td class='total'><%=totalSumatorio%></td> </tr>
            </table>
            
            <br>

            <input type="submit" name="enviar" value="Pagar">
        </form>
        <br><br>

        <hr><hr>
        <a href="catalogo.jsp">Volver</a>
        <hr><hr>

        <br><a href="login.jsp">Volver al Login</a><br><br>

    </body>
</html>
