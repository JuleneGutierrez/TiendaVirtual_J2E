<%-- 
    Document   : login
    Created on : 18-feb-2024, 15:06:00
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
       
        <h1>Iniciar Sesion</h1>
        
        <form method="POST" action="ServerControlador">
            Usuario: 
            <input type="text" name="usuario" value=""/><br><br>
            Contraseña:
            <input type="password" name="contrasena" value=""/><br><br>
            
            <input type="submit" value="Registro" name="enviar"/>
            <input type="submit" value="Entrar" name="enviar"/>

        </form>
    
    </body>
</html>
