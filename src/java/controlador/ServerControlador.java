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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Cesta;
import modelo.Producto;

/*Importamos la clase producto para poder hacer uso de ello*/
/**
 *
 * @author Julencia
 */
public class ServerControlador extends HttpServlet
{

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
            throws ServletException, IOException, SQLException
    {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        /*Aquí se obtiene una sesión HTTP asociada con la solicitud actual. Si no hay una sesión existente,
        se crea una nueva. El parámetro true indica que se debe crear una nueva sesión si no existe una sesión actual.*/
        HttpSession session = request.getSession(true);

        /*Creamos una instancia de la clase conexion*/
        Conexion conexion = new Conexion();

        /*Usamos la interfaz RequestDispatcher  para reenviar la solicitud al resto de servlet o jsp */
        RequestDispatcher rd = null;

        try
        {

            /*Creamos una variable para guardar la ruta hacia donde se redirigira al usuario*/
            String ruta = "";

            /*Recojo el value de los botones de login y registro y en funcion de cual*/
            String botonSeleccionado = request.getParameter("enviar");

            if ("Entrar".equals(botonSeleccionado))
            {
                // Obtener los parámetros del formulario JSP
                String usuario = request.getParameter("usuario");
                String contrasena = request.getParameter("contrasena");

                /*Usamos el objeto de Tipo Connection para conectar a la bbdd y usamos el  metodo conectarBd*/
                conexion.conectarBD();

                /*Obtengo la respuesta a la consulta realizada con el metodo verCredenciales pasando por parametro el usuario y 
                la contraseña recogidas del form Y LO RECOJE EL OBJETO RESULTSET*/
                ResultSet resultSet = conexion.verCredencial(usuario, contrasena);

                try
                {
                    /*Si al menos hay un registro coincidente entra en el if, es decir recorremos el registro devuelto*/
                    if (resultSet.next())
                    {
                        /*En caso de que sea asi obtenemos el rol de ese usuario y el nombre*/
                        String rol = resultSet.getString("rol");
                        String nombrePers = resultSet.getString("nombre");
                        String idUsuario = resultSet.getString("id_usuario");
                        String usuariolog = resultSet.getString("usuario");

                        /*Guardo en una variable de sesion el rol del usuario y su nombre*/
                        session.setAttribute("rol", rol);
                        session.setAttribute("nombre", nombrePers);
                        session.setAttribute("id_usuario", idUsuario);
                        session.setAttribute("usuario", usuariolog);

                        /*COMPROBACIONES DE FUNCIONAMIENTO*/
                        System.out.println("¡Credenciales válidas!");
                        System.out.println(rol);
                        System.out.println(nombrePers);
                        System.out.println(idUsuario);

                        /*CONDICIONAR ROLES PARA ACCESO SIN LOGEO Y CON LOGEO ----------- USAR ELSE IF ANALIZANDO COMPRADOR Y VENDEDOR  REDIRECCION
                        A MENU Y SI NO ES NINGUNO (ELSE) REDIRECCION A LOGIN Y DESTRUCCION DE SESIONS*/
                        /*Al igual que en php desde aqui ya redirigimos al usuario en caso de ser rol invitado*/
                        if ("invitado".equals(rol))
                        {
                            ruta = "/previsualizacion.jsp";
                        } else
                        {
                            /*En caso de que sea cualquier otro rol se ira al menu*/
                            ruta = "/menu.jsp";
                        }

                    } else
                    {
                        System.out.println("¡Credenciales INCORRECTAS!");
                        ruta = "/login.jsp";

                    }
                } catch (SQLException ex)
                {
                    // Manejar la excepción aquí
                    ex.printStackTrace(); // Imprimir la traza de la excepción (solo para propósitos de depuración)
                }
                
                /*GESTION BOTON REGISTRARSE (DE LOGIN.JSP)  */
            } else if ("Registrarse".equals(botonSeleccionado))
            {
                ruta = "/registro.jsp";

                /*GESTION BOTON CONFIRMAR (DE REGISTRO.JSP)COMPRADOR */
            } else if ("Confirmar".equals(botonSeleccionado))
            {

                /*Recojo todos los datos del formulario*/
                String nombre = request.getParameter("nombre");
                String apellido = request.getParameter("apellido");
                String nombreRegistro = request.getParameter("usuario");
                String contrasena = request.getParameter("contrasena");
                String email = request.getParameter("email");
                String telefono = request.getParameter("telefono");
                String dni = request.getParameter("dni");

                /*Pruebas de que funciona el recojer los datos del form*/
                System.out.println(nombre);
                System.out.println(apellido);
                System.out.println(nombreRegistro);
                System.out.println(contrasena);
                System.out.println(email);
                System.out.println(telefono);
                System.out.println(dni);

                /*Utilizamos el metodo creado insertarUsuario y le pasamos por parametro las variables con los datos recogidos del formulario*/
                conexion.insertarUsuario(nombre, apellido, nombreRegistro, contrasena, email, telefono, dni);

                ruta = "/menu.jsp";

                /*GESTION BOTON COMPRAR (DEL MENU.JSP)COMPRADOR */ 
            } else if ("Comprar".equals(botonSeleccionado))
            {
                ruta = "/catalogo.jsp";

                /*Para enviar el array de libros a traves de la session necesito extraer los registros reogidos por resulset y meterlos en el aaray
                list de productos,para ello uso el metodo creado  de obtener libros*/
                ResultSet recogerLibros = conexion.obtenerLibros();

                /*Creo el arrayList de productos llamado todosLibros*/
                ArrayList<Producto> todosLibros = new ArrayList();

                try
                {
                    /*Uso el metodo .next() para recoger los registros extraidos por resulset */
                    while (recogerLibros.next())
                    {
                        String ptitulo = recogerLibros.getString("titulo");
                        String pautor = recogerLibros.getString("autor");
                        String peditorial= recogerLibros.getString("editorial");
                        double pPrecio = recogerLibros.getDouble("precio");
                        
                        /*Creo un nuevo Objeto producto con los datos correspondientes a cada libro de la BD */
                        Producto datosLibro = new Producto(ptitulo, pPrecio, pautor, peditorial);
                        
                        /*Añado al arrayList creado el producto creado con la informacion de los libros extraidos de la Bd*/
                        todosLibros.add(datosLibro);
                        
                    }
                } catch (SQLException e)
                {
                    System.out.println(e.getMessage());
                }

                session.setAttribute("libros", todosLibros);
                //session.setAttribute("Libros",conexion.obtenerLibros());
                
              /*GESTION BOTON CHECKOUT DE LA CESTA (CATALOGO.JSP) COMPRADOR*/ 
            } else if("Checkout".equals(botonSeleccionado))
            {
                ruta = "/pagar.jsp";
                
              /*GESTION BOTON PAGAR CESTA (PAGAR.JSP)COMPRADOR*/ 
            } else if("Pagar".equals(botonSeleccionado))
            {
                /*Obtenemos  de las variables de sesion el id del usuario, el total pagado, y su cesta para poder recogerlo en metodo
                al que llamamos mas abajo*/
                
                int idUsuario = Integer.parseInt((String) session.getAttribute("id_usuario"));
                double sumatorio = (double) session.getAttribute("sumatorio");
                Cesta cesta = (Cesta) session.getAttribute("cesta");
                
                /*Llamamos al metodo para insertar el pedido pagado del usuario */
                conexion.insertarPedido(idUsuario, sumatorio, cesta);
                
                /*Una vez que ha pulsado pagar en pago le lleva al menu */
                ruta = "/menu.jsp";
                
              /*GESTION BOTON ESTADO PEDIDO (MENU.JSP) COMPRADOR*/  
            } else if("EstadoPedido".equals(botonSeleccionado))
            {
                
                ruta = "/verPedidosC.jsp";
              
              /*GESTION BOTON DE FILTRAR PEDIDOS (VERPEDIDOS.JSP) COMPRADOR*/ 
            } else if ("Filtrar Pedido".equals(botonSeleccionado))
            {
                /*Recojemos el id del usuario para pasarlo por parametro al metodo de buscar pedidos, para que solo nos muestre los pedidos
                de dicho usuario*/
                
                int idUsuario = Integer.parseInt((String) session.getAttribute("id_usuario"));
            
                /*Recojemos la opcion selecionada en el select, donde el usuario puede ver sus pedidos usando un filtro en cuanto al estado*/
                String opcionSelect = request.getParameter("opcion");
                
                ResultSet pedidos = conexion.verPedidoC(idUsuario, opcionSelect);
                
                request.setAttribute("pedidos", pedidos);
                
                ruta = "/verPedidosC.jsp";
            }
          


            /*Redirigo la peticion */
            rd = getServletContext().getRequestDispatcher(ruta);
            rd.forward(request, response);

        } finally
        {
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
            throws ServletException, IOException
    {
        try
        {
            processRequest(request, response);
        } catch (SQLException ex)
        {
            Logger.getLogger(ServerControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            throws ServletException, IOException
    {
        try
        {
            processRequest(request, response);
        } catch (SQLException ex)
        {
            Logger.getLogger(ServerControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

}
