/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import conexion.Conexion;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Julencia
 */
public class ServerControlador extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        /*Aquí se obtiene una sesión HTTP asociada con la solicitud actual. Si no hay una sesión existente,
        se crea una nueva. El parámetro true indica que se debe crear una nueva sesión si no existe una sesión actual.*/
        HttpSession session = request.getSession(true);

        /*Creamos una instancia de la clase conexion*/
        Conexion conexion = new Conexion();

        /*Usamos la interfaz RequestDispatcher  para reenviar la solicitud al resto de servlet o jsp */
        RequestDispatcher rd = null;

        try {

            /*Creamos una variable para guardar la ruta hacia donde se redirigira al usuario*/
            String ruta = "";

            /*Recojo el value de los botones de login y registro y en funcion de cual*/
           
           String botonSeleccionado = request.getParameter("enviar");
            
           
            if ("Entrar".equals(botonSeleccionado)) {
                // Obtener los parámetros del formulario JSP
                String usuario = request.getParameter("usuario");
                String contrasena = request.getParameter("contrasena");

                /*Usamos el objeto de Tipo Connection para conectar a la bbdd y usamos el  metodo conectarBd*/
                conexion.conectarBD();

                /*Obtengo la respuesta a la consulta realizada con el metodo verCredenciales pasando por parametro el usuario y 
                la contraseña recogidas del form Y LO RECOJE EL OBJETO RESULTSET*/
                ResultSet resultSet = conexion.verCredencial(usuario, contrasena);

                try {
                    /*Si al menos hay un registro coincidente entra en el if*/
                    if (resultSet.next()) {

                        String rol = resultSet.getString("rol");
                        out.println("¡Credenciales válidas!");
                        //out.println(rol);

                         ruta = "/menu.jsp";
                        // System.out.println("Ruta de redirección: " + ruta);
                    } else {

                        // ruta = "/menu.jsp";
                        // out.println("¡Credenciales inválidas!");
                    }
                } catch (SQLException ex) {
                    // Manejar la excepción aquí
                    ex.printStackTrace(); // Imprimir la traza de la excepción (solo para propósitos de depuración)
                }
            }

            rd = getServletContext().getRequestDispatcher(ruta);
            rd.forward(request, response);

        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
