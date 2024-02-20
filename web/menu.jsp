<%-- 
    Document   : menu
    Created on : 18-feb-2024, 19:57:50
    Author     : Julencia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>


        <%if (session.getAttribute("usuario") != null)
            {%>
        <h1>MENÚ</h1>


        <h1>Bienvenido, <%=session.getAttribute("nombre")%>!</h1>


        <form method="POST" action="ServerControlador">
            <% if ("vendedor".equals(session.getAttribute("rol")))
                { %>

            <input type="submit" value="verSolicitudes" name="enviar" />
            <input type="submit" value="CambiarEstados" name="enviar" />

            <% } else if ("comprador".equals(session.getAttribute("rol")))
            { %>


            <input type="submit" value="Comprar" name="enviar" />
            <input type="submit" value="EstadoPedido" name="enviar" />
        </form>

        <% }%>
        <% }else{%> <h3>ACCESO NO PERMITIDO</h3> <%}%>
    </body>
</html>
