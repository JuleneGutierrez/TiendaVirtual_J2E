/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Julencia
 */
@WebServlet(name = "Cesta", urlPatterns = {"/Cesta"})
public class Cesta extends HttpServlet {

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
        
    
    }
    private Producto[] productos;

        // Constructores
        public Cesta(Producto[] productos) {
            this.productos = productos;
        }
        public Cesta( ) {
            this.productos = new Producto[0];
        }

        // Método getter para acceder al array
        public Producto[] getProductos() {
            return productos;
        }
        // Método setter para modificar el array
        public void setProductos(Producto[] productos) {
            this.productos = productos;
        }
        /* Creamos un metodo en la clase cesta para agregar nuevos libros */
        public void agregarProducto(Producto pProducto){
            this.productos[this.productos.length] = pProducto;
        }
        // Implementación del método serialize
        public Producto[] serialize(){
            return productos;
        }
        // Implementación del método unserialize
        public void deserializar(byte[] datos) {
            try {
                ByteArrayInputStream entradaBytes = new ByteArrayInputStream(datos);
                ObjectInputStream entradaObjetos = new ObjectInputStream(entradaBytes);
                this.productos = (Producto[]) entradaObjetos.readObject();
                entradaObjetos.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        // Método para buscar un producto determinado por título
        // Si encuentra coincidencias dentro del array, retorna el producto
        // Si no encuentra ninguna coincidencia, retorna null 
        public Producto buscarProductoPorTitulo(String tituloDelProducto) {
            for (Producto producto : this.productos) {
                if (producto.obtenerTitulo().equals(tituloDelProducto)) {
                    return producto;
                }
            }
            return null;
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
