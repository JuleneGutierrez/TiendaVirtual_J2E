/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Julencia
 */
//@WebServlet(name = "conexion", urlPatterns = {"/conexion"})
public class Conexion extends HttpServlet {

    /*Declaramos una variable de tipo Connection de la clase DriverManager, para despues almacenar  donde despues*/
    Connection conexion;

    /*Atributos necesarios para conectarse a la BBDD*/
    private String url;
    private String usuario;
    private String contrasena;

    /*Constructor para conexion por defecto*/
    public Conexion() {
        /*Asigno a los atributos lo correspondiente que sera por defecto*/
        this.url = "jdbc:mysql://localhost/tienda";
        this.usuario = "julene";
        this.contrasena = "julene";

        try {
            /*Cargamos el driver JDBC para permitir una comunicacion con la base de datos*/
            Class.forName("com.mysql.jdbc.Driver").newInstance();

        } catch (ClassNotFoundException ex) {
            // Imprimir un mensaje de error en la consola
            System.err.println("Error: No se pudo encontrar el driver de MySQL.");

        } catch (InstantiationException ex) {
            // Imprimir un mensaje de error en la consola
            System.err.println("Error: No se pudo instanciar el driver de MySQL.");

        } catch (IllegalAccessException ex) {
            // Imprimir un mensaje de error en la consola
            System.err.println("Error: No se pudo acceder.");

        }

    }

    /*Metodo usado para conectarse a la BD*/
    public void conectarBD() {
        try {
            /*Nos conectamos a la bbdd usando la clase Connection */
            conexion = DriverManager.getConnection(this.url, this.usuario, this.contrasena);

        } catch (SQLException ex) {
            System.err.println("Error al conectar a la base de datos: ");
        }

    }

    /*Metodo usado para desconectarse de la BD*/
    public void desconectarBD() {
        try {
            /*Se cierra la conexion*/
            conexion.close();
            
        } catch (SQLException ex) 
        {
            System.err.println("Error al desconectar a la base de datos");
        }
    }
    
    
    
    /*Metodo usado HACER CONSULTA*/
    public ResultSet obtenerLibros()
    {
        /*Usamos el metodo encargado de iniciar la conexion con la BD*/
        conectarBD();

        /*Creamos un objeto statement*/
        Statement stmt;
        try
        {
            /*Llamamos al metodo del objeto Connection createStatement*/
            // Paso 3: Crear sentencias SQL, utilizando objetos de tipo Statement
            stmt = conexion.createStatement();

            // Paso 4: Ejecutar las sentencias SQL a traves de los objetos Statement
            String sqlStr = "select * from usuario;";

            /*Se almacenan los datos en el objeto ResultSet*/
            ResultSet rset = stmt.executeQuery(sqlStr);

            /*Se retornan los datos de la sentencia SQL*/
            return rset;
            
        } catch (SQLException ex)
        {
            return null;
        }
    }
    
    
       
    /*Metodo usado HACER CONSULTA
    COMENTARIO DE PRUEBA*/
    public ResultSet verCredencial(String usuario, String contrasena)
    {
        /*Usamos el metodo encargado de iniciar la conexion con la BD*/
        conectarBD();

        /*Creamos un objeto statement*/
        Statement stmt;
        try
        {
            /*Llamamos al metodo del objeto Connection createStatement*/
            // Paso 3: Crear sentencias SQL, utilizando objetos de tipo Statement
            stmt = conexion.createStatement();

            // Paso 4: Ejecutar las sentencias SQL a traves de los objetos Statement
            String sqlStr = "SELECT * FROM usuario WHERE usuario = '" + usuario + "' AND contrasena = '" + contrasena + "'";

            /*Se almacenan los datos en el objeto ResultSet*/
            ResultSet rset = stmt.executeQuery(sqlStr);

            /*Se retornan los datos de la sentencia SQL*/
            return rset;
            
        } catch (SQLException ex)
        {
            return null;
        }
    }

}
