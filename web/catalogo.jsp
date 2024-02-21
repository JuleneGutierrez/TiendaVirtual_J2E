<%-- 
    Document   : catalogo
    Created on : 20-feb-2024, 0:43:06
    Author     : Julencia
--%>

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

            table{

                width: 80%;

            }
            tr
            {
                height: 40px
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
                    <td><%=libros.get(i).getPrecio()%></td>
                    <td><%=libros.get(i).getEditorial()%></td>
                </tr>


                <% }%>

            </table>

            <br>
            <input type="submit" value="agregar" name="enviar" />


        </form>


        <%
            
            String botonSeleccionado = request.getParameter("enviar");
            if ("agregar".equals(botonSeleccionado))
            {
                System.out.println("El botón 'Añadir a la cesta' ha sido seleccionado.");
                // Aquí puedes agregar la lógica para procesar los productos agregados a la cesta

                /*Creamos una nueva cesta */
                Cesta nuevaCesta = new Cesta();

                /*Creamos un objetos gestioCesta que */
                GestionCesta gestionCesta = new GestionCesta();

                /* Llamo a la funcion catalogoProducto y le paso como parametro las claves valor de todo el formulario, esta funcion me devuelve un ArrayList con los productos */
                ArrayList<Producto> arrayProductos = gestionCesta.catalogoProducto(request.getParameterMap());

                /* Imprimo los productos para comprobar que funciona */
                if (arrayProductos.size()>0)
                {
                    out.print(arrayProductos);
                    
                    //nuevaCesta.agregarProducto(producto);
                    
                    /*Agregar los productos a la cesta, con un for que recorra el arrayProductos y que meta en la cesta nuevaCesta esos productos
                    Despues de esto generar la tabla , cargando la nuevaCesta en las columnas,  y en esta tabla añadir una nueva columna con un select para eliminar,
                    despues, en la clase GestionCesta*/
                    
                    /*EN EL METODO DE AGREGAR INTRODUCTO EL METODO BUSCAR Y SI EXISTE EL OBJETO PRODUCTO POR TITULO MODIFICAMOS LA CANTIDAD EN UNO*/
                    /*CON LA SELECT TENGO QUE OBTENER EL VALOR DE LA CELDA PARA CREAR UN METODO ELIMINAR QUE SEA . REMOVE() Y POR PARAMETRO LE PASO LA CELDA 
                    QUE HEMOS OBTENIDO CON EL SELECT*/
                    
                    
                } else
                {
                    out.print("No hay productos");
                }
                
                
            }
            
            out.print(botonSeleccionado);
        %>

    </body>
</html>
