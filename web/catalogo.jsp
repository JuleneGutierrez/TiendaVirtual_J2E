

<%@page import="modelo.GestionCesta"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="modelo.Cesta"%>
<%@page import="java.util.ArrayList"%>
<%@page import="modelo.Producto"%>
<%@page import="java.sql.ResultSet"%>
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
        <%if (session.getAttribute("rol") != null && session.getAttribute("rol").equals("comprador")) {%>
        <h1>ESTAS EN CATALOGO</h1>

        <form name="enviar" action="catalogo.jsp" method="POST">
            <table>
                <tr>
                    <th>Cantidad</th>
                    <th>Titulo</th>
                    <th>Autor</th>
                    <th>precio</th>
                    <th>Editorial</th>
                </tr>

                <%
                    /*Creamos una nueva cesta con los datos de la sesion cesta */
                    Cesta nuevaCesta = (Cesta) session.getAttribute("cesta");

                    /* Si la sesion no tiene cesta, creo una nueva cesta para guardar los productos */
                    if (nuevaCesta == null)
                    {
                        nuevaCesta = new Cesta();
                    }

                    /*Creamos un arraylist donde descargo el arrayLista pasado por variable de sesion desde el controlador con los libros de la BD*/
                    ArrayList<Producto> libros = (ArrayList<Producto>) session.getAttribute("libros");

                    /*Recorremos el tamaño del arraylist que contiene todos los libros que se encuentran en la base de datos*/
                    for (int i = 0; i < libros.size(); i++)
                    {
                %>

                <tr>
                    <td> 
                        <input type='number' name='cantidad[<%=libros.get(i).getTitulo() + " | " + libros.get(i).getPrecio()%>]' value='0' id= 'input'>
                    </td>
                    <td><%=libros.get(i).getTitulo()%></td>
                    <td><%=libros.get(i).getAutor()%></td>
                    <td><%=String.format("%.2f", libros.get(i).getPrecio())%> €</td>
                    <td><%=libros.get(i).getEditorial()%></td>
                </tr>


                <% }%>

            </table>

            <br>
            <input type="submit" value="agregar" name="enviar" /><br><br><hr>


        </form>


        <%
            /* Se recoge el contenido del boton del primer formulario agregar */
            String botonSeleccionado = request.getParameter("enviar");

            /* Se obtiene el contenido del boton de eliminar registro de la cesta, este contenido tiene el numero de la celda del producto a eliminar */
            String botonBorrar = request.getParameter("borrar");

            /* Si se ha pulsado uno de los botones de eliminar, entra en el if */
            if (botonBorrar != null)
            {
                /* Llamamos al metodo de la cesta encargado de eliminar un producto cuya celda se envie por parametro */
                nuevaCesta.borrarProducto(botonBorrar);
            }

            /* Si se ha pulsado el boton de agregar o el boton de eliminar, entra en el if  para que se actualice la tabla cuando se modifica*/
            if ("agregar".equals(botonSeleccionado) || botonBorrar != null)
            {
                System.out.println("El botón 'Añadir a la cesta' ha sido seleccionado.");

                /*Creamos un objetos gestioCesta que gestionara la obtencion de todas las claves valor del formulario, 
                    para solo coger las cantidades de los productos seleccionados */
                GestionCesta gestionCesta = new GestionCesta();

                /* Llamo a la funcion catalogoProducto y le paso como parametro las claves valor de todo el formulario, esta funcion me devuelve un ArrayList con los productos */
                ArrayList<Producto> arrayProductos = gestionCesta.catalogoProducto(request.getParameterMap());

                /* Se agregan los productos obtenidos del formulario, se usa la funcion agregarProductos de la clase Cesta para agregarlos */
                nuevaCesta.agregarProductos(arrayProductos);

                /* Guardamos la cesta modificada en la sesion */
                session.setAttribute("cesta", nuevaCesta);

                /* Imprimo los productos para comprobar que funciona */
                if (nuevaCesta.getArrayProductos().size() > 0)
                {

                    /* Se crea el formulario que mostrar los productos agregados en la cesta */
        %>
        <form name="enviar" action="catalogo.jsp" method="POST">
            <table>
                <tr>
                    <th>Titulo</th>
                    <th>Precio Unitario</th>
                    <th>Cantidad</th>
                    <th>Eliminar</th>
                </tr>

                <% /*Recorremos el tamaño de la cesta que contiene todos los productos guardados en ella*/
                    for (int i = 0; i < nuevaCesta.getArrayProductos().size(); i++)
                    {
                %>

                <tr>
                    <td><%=nuevaCesta.getArrayProductos().get(i).getTitulo()%></td>
                    <td><%=String.format("%.2f", nuevaCesta.getArrayProductos().get(i).getPrecio())%> €</td>
                    <td><%=nuevaCesta.getArrayProductos().get(i).getCantidad()%></td>
                    <td><button type="submit" value="<%=i%>" name="borrar">Eliminar</button></td> <!-- Se guarda el valor de la posicion en el array dentro de cesta -->
                </tr>


                <% }%>

            </table>
        </form>
        <br>
        <form name="enviar" action="ServerControlador" method="POST">
            <input type="submit" value="Checkout" name="enviar" />
        </form>
        <%
                } else
                {
                    out.print("No hay productos en la cesta");
                }

            }
        %>
        <br><hr>
        <form method="POST" action="ServerControlador">
            <label for="vueltaMenu">¿Qué más necesitas hacer?</label><br>
            <input type="submit" name="enviar" value="Volver al menu" />

        </form><br>
        <% } else {%> <h3>ACCESO NO PERMITIDO</h3> <%}%>
    </body>
</html>
