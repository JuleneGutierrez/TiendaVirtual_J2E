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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Cesta;
import modelo.Producto;
import org.jboss.weld.servlet.SessionHolder;

/**
 * No me mueras plisss
 *
 * @author Julencia
 */
//@WebServlet(name = "conexion", urlPatterns = {"/conexion"})
public class Conexion extends HttpServlet
{

    /*Declaramos una variable de tipo Connection de la clase DriverManager, para despues almacenar  donde despues*/
    Connection conexion;

    /*Atributos necesarios para conectarse a la BBDD*/
    private String url;
    private String usuario;
    private String contrasena;

    /*Constructor para conexion por defecto*/
    public Conexion()
    {
        /*Asigno a los atributos lo correspondiente que sera por defecto*/
        this.url = "jdbc:mysql://localhost/tienda";
        this.usuario = "julene";
        this.contrasena = "julene";

        try
        {
            /*Cargamos el driver JDBC para permitir una comunicacion con la base de datos*/
            Class.forName("com.mysql.jdbc.Driver").newInstance();

        } catch (ClassNotFoundException ex)
        {
            // Imprimir un mensaje de error en la consola
            System.err.println("Error: No se pudo encontrar el driver de MySQL.");

        } catch (InstantiationException ex)
        {
            // Imprimir un mensaje de error en la consola
            System.err.println("Error: No se pudo instanciar el driver de MySQL.");

        } catch (IllegalAccessException ex)
        {
            // Imprimir un mensaje de error en la consola
            System.err.println("Error: No se pudo acceder.");

        }

    }

    /*Metodo usado para conectarse a la BD*/
    public void conectarBD()
    {
        try
        {
            /*Nos conectamos a la bbdd usando la clase Connection */
            conexion = DriverManager.getConnection(this.url, this.usuario, this.contrasena);

        } catch (SQLException ex)
        {
            System.err.println("Error al conectar a la base de datos: ");
        }

    }

    /*Metodo usado para desconectarse de la BD*/
    public void desconectarBD()
    {
        try
        {
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
            String sqlStr = "select * from libro;";

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

    /*METODO PARA REALIZAR UN INSERT EN LA BD*/
    public void insertarUsuario(String nombre, String apellido, String usuario, String contrasena, String email, String telefono, String dni)
    {
        /*Usamos el metodo encargado de iniciar la conexion con la BD*/
        conectarBD();

        //PreparedStatement preparedStatement = null;
        try
        {
            //Preparamos la sentencia SQL con una sentencia preparada para evitar la inyección SQL
            String sqlStr = "INSERT INTO usuario (nombre, apellido, usuario, contrasena, email, telefono,dni) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conexion.prepareStatement(sqlStr);

            //Establecemos los valores de los parámetros en la sentencia preparada
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellido);
            preparedStatement.setString(3, usuario);
            preparedStatement.setString(4, contrasena);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, telefono);
            preparedStatement.setString(7, dni);

            //Ejecutamos la consulta
            preparedStatement.executeUpdate();

            // System.out.println(preparedStatement.executeUpdate());
            //Cerramos los recursos
            preparedStatement.close();

            /*GESTIONAR CON UN IF SI PREPAREDSTATEMENT DEVUELVE FALSE QUE RETORNE UN MENSAJE, PARA ESO CAMBIAR EL METO DE VOID A STRING O ALGO*/
        } catch (SQLException ex)
        {

            //Manejamos la excepción imprimiendo el mensaje de error
            ex.printStackTrace();

        }
    }

    /*METODO PARA REALIZAR UN INSERT EN LA TABLA PEDIDOS, Y DETALLE PEDIDOSS EN LA  BD*/
    
    public void insertarPedido(int idUsuario, double sumatorio, Cesta cesta)
    {
        /*Usamos el metodo encargado de iniciar la conexion con la BD*/
        conectarBD();

        //PreparedStatement preparedStatement = null;
        try
        {
            
        // INSERCION EN LA TABLA PEDIDOS EL ID DE USUARIO Y EL FACTURADO, YA QUE EL ESTADO ES POR DEFECTO
            
         
            /*Guardamos en sqlStr la consulta de insert de las columnas de id_usuario y facturado de la tabla pedido*/
            String sqlStr = "INSERT INTO pedido (id_usuario,facturado) VALUES (?,?)";

            /*Usamos una consulta preparada utilizando el objeto PreparedStatement evitando asi la inyeccion de codigo SQL*/
            PreparedStatement preparedStatement = conexion.prepareStatement(sqlStr);

            /* Ahora si damos los prametros del insert*/
            preparedStatement.setInt(1, idUsuario);
            preparedStatement.setDouble(2, sumatorio);

            /*Usamos el metodo executeUpdate del objeto preparedStatement para realizar la sentencia insert*/
            preparedStatement.executeUpdate();

        // UNA VEZ REGISTRADO EL PEDIDO EN LA TABLA PEDIDOS, ES NECESARIO TAMBIEN INSERTAR LO REFERENTE DE ESTE PEDIDO EN DETALLES PEDIDO
           
            /*Con esta consulta buscamos el pedido con el id de pedido maximo (que seria el que acabamos de registrar) correspondiente al id 
            del usuario que lo ha realizado*/
            sqlStr = "SELECT MAX(id_pedido) as pedidoMaximo FROM pedido WHERE id_usuario=?";

            preparedStatement = conexion.prepareStatement(sqlStr);

            /*Pasamos los parametros de la sentencia*/
            preparedStatement.setInt(1, idUsuario);

            /*Obtener los registros coincidentes con la select, en este caso solo sera uno*/
            ResultSet rset = preparedStatement.executeQuery();
            rset.next();

            /*Guardadmos ahora en la variable idPedidoMax el pedido correspondiente*/
            int idPedidoMax = rset.getInt("pedidoMaximo");
            
        //INSERCION DE LOS DETALLES PEDIDO, PARA ELLO HEMOS NECESITADO OBTENER EL ULTIMO PEDIDO (SELECT ANTERIOR)
            
            /*Recorremos el array de productos de la cesta del usuario*/
            for (int i = 0; i < cesta.getArrayProductos().size(); i++)
            {
                /*Inser en detalle pedido, la columa del titulo del libro, el id  y su cantidad, ya que se mostrara desglosado en la BBDD */
                sqlStr = "INSERT INTO detallepedido (tituloLibro,id_pedido,cantidad) VALUES (?,?,?)";

                /*Usamos una consulta preparada utilizando el objeto PreparedStatement evitando asi la inyeccion de codigo SQL*/
                preparedStatement = conexion.prepareStatement(sqlStr);

                /* Ahora si damos los prametros del insert*/
                preparedStatement.setString(1, cesta.getArrayProductos().get(i).getTitulo());
                preparedStatement.setInt(2, idPedidoMax);
                preparedStatement.setInt(3, cesta.getArrayProductos().get(i).getCantidad());

                /*Usamos el metodo executeUpdate del objeto preparedStatement para realizar la sentencia insert*/
                preparedStatement.executeUpdate();
            }

            //Cerramos los recursos
            preparedStatement.close();

        } catch (SQLException ex)
        {

            //Manejamos la excepción imprimiendo el mensaje de error
            ex.printStackTrace();

        }
    }

}
