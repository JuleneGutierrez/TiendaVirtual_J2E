<%-- 
    Document   : catalogo
    Created on : 20-feb-2024, 0:43:06
    Author     : Julencia
--%>

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

                //String[] cantidades = request.getParameterValues("cantidad[]");
                //out.print(cantidades);
                // Obtener los parámetros del request
                
                /*Usamos la interfaz para definir que los parametros que le pasamos desde el fot (en el name, titulo y precio) y en value la cantidad para que se
                almacenen de forma clave-valor */
                Map<String, String[]> parametros = request.getParameterMap();

                /*Con este "foreach" obtenemos del map las claves compuestas como hemos mencionado con el titulo y precio*/
                for (String clave : parametros.keySet())
                {
                    
                    /*Entra en el if si el parametro extraido comienza por cantidad[, ya que en realida estamos recogiendo todas las claves de los input*/
                    if (clave.startsWith("cantidad["))
                    {
                        /*Creamos un array de tipo String, ahora en este caso para guardar la cantidad real de libros que quiere comprar el usuario,
                        para ello usamos el metodo get y le damos la clave sobre la cual queremos el valor, es decir la cantidad de libros.*/
                        String[] valores = parametros.get(clave);
                        
                        /*Si hemo podido obetener el valor en el array anterior, entra en este if */
                        if (valores.length > 0)
                        {
                            String[] partesClave = clave.substring(9).split(" | ");
                            /*Creamos variable para guardar el nombre del libro, utilizando el metodo substring para extraer de la clave solo el nombre*/
                            // Obtener el nombre del libro desde el nombre del parámetro
                            String nombreLibro = partesClave[0];
                            // Obtener el precio del libro desde el nombre del parámetro
                            String precioLibro =  partesClave[2].replace("]", " ");
                            // Obtener la cantidad del libro del array de valores
                            int cantidad = Integer.parseInt(valores[0]);
                            
                            
                            if(cantidad > 0)
                            {
                               // nuevaCesta.agregarProducto(new Producto(nombreLibro, Integer.parseInt(precioLibro), cantidad));
                                out.println(nombreLibro);
                                out.println(precioLibro);
                                out.println(cantidad);
                            }
                        }
                    }
                }
            }

            out.print(botonSeleccionado);
        %>

    </body>
</html>
