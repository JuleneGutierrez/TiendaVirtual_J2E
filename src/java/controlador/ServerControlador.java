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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Cesta;
import modelo.DetallePedido;
import modelo.Pedido;
import modelo.Producto;
import modelo.Solicitud;

/*Importamos la clase producto para poder hacer uso de ello*/
/**
 *
 * @author Julencia
 */
public class ServerControlador extends HttpServlet {

    private static String MENU = "/menu.jsp";
    private static String LOGIN = "/login.jsp";
    private static String REGISTRO = "//registro.jsp";
    private static String CATALOGO = "/catalogo.jsp";
    private static String PAGAR = "/pagar.jsp";
    private static String PREVISUALIZACION = "/previsualizacion.jsp";
    private static String VER_PEDIDOS_COMPRADOR = "/verPedidosC.jsp";
    private static String VER_PEDIDOS_VENDEDOR = "/verPedidosV.jsp";
    private static String VER_SOLICTUDES = "/verSolicitudes.jsp";

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
            throws ServletException, IOException, SQLException {
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
                    /*Si al menos hay un registro coincidente entra en el if, es decir recorremos el registro devuelto*/
                    if (resultSet.next()) {
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
                        if ("invitado".equals(rol)) {
                            ResultSet recogerLibros = conexion.obtenerLibros();
                            /*Creo el arrayList de productos llamado todosLibros*/
                            ArrayList<Producto> todosLibros = new ArrayList();

                            try {
                                /*Uso el metodo .next() para recoger los registros extraidos por resulset */
                                while (recogerLibros.next()) {
                                    String ptitulo = recogerLibros.getString("titulo");
                                    String pautor = recogerLibros.getString("autor");
                                    String peditorial = recogerLibros.getString("editorial");
                                    double pPrecio = recogerLibros.getDouble("precio");

                                    /*Creo un nuevo Objeto producto con los datos correspondientes a cada libro de la BD */
                                    Producto datosLibro = new Producto(ptitulo, pPrecio, pautor, peditorial);

                                    /*Añado al arrayList creado el producto creado con la informacion de los libros extraidos de la Bd*/
                                    todosLibros.add(datosLibro);

                                }
                            } catch (SQLException e) {
                                System.out.println(e.getMessage());
                            }

                            session.setAttribute("libros", todosLibros);

                            ruta = PREVISUALIZACION;
                        } else {
                            /*En caso de que sea cualquier otro rol se ira al menu*/
                            ruta = MENU;
                        }

                    } else {
                        System.out.println("¡Credenciales INCORRECTAS!");
                        ruta = LOGIN;

                    }
                } catch (SQLException ex) {
                    // Manejar la excepción aquí
                    ex.printStackTrace(); // Imprimir la traza de la excepción (solo para propósitos de depuración)
                }

                /*GESTION BOTON REGISTRARSE (DE LOGIN.JSP)  */
            } else if ("Registrarse".equals(botonSeleccionado)) {
                ruta = REGISTRO;
            } else if ("Volver al login".equals(botonSeleccionado)) {
                ruta = LOGIN;
                /*GESTION BOTON CONFIRMAR (DE REGISTRO.JSP)COMPRADOR */
            } else if ("Confirmar".equals(botonSeleccionado)) {

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
                boolean comprobante = conexion.insertarUsuario(nombre, apellido, nombreRegistro, contrasena, email, telefono, dni);
                if (comprobante) {

                    ruta = MENU;
                } else {
                    session.setAttribute("registroError", "No se pudo crear el regitro, introduce otro usurio");
                    ruta = REGISTRO;
                }


                /*GESTION BOTON COMPRAR (DEL MENU.JSP)COMPRADOR */
            } else if ("Comprar".equals(botonSeleccionado)) {
                ruta = CATALOGO;

                /*Para enviar el array de libros a traves de la session necesito extraer los registros reogidos por resulset y meterlos en el aaray
                list de productos,para ello uso el metodo creado  de obtener libros*/
                ResultSet recogerLibros = conexion.obtenerLibros();

                /*Creo el arrayList de productos llamado todosLibros*/
                ArrayList<Producto> todosLibros = new ArrayList();

                try {
                    /*Uso el metodo .next() para recoger los registros extraidos por resulset */
                    while (recogerLibros.next()) {
                        String ptitulo = recogerLibros.getString("titulo");
                        String pautor = recogerLibros.getString("autor");
                        String peditorial = recogerLibros.getString("editorial");
                        double pPrecio = recogerLibros.getDouble("precio");

                        /*Creo un nuevo Objeto producto con los datos correspondientes a cada libro de la BD */
                        Producto datosLibro = new Producto(ptitulo, pPrecio, pautor, peditorial);

                        /*Añado al arrayList creado el producto creado con la informacion de los libros extraidos de la Bd*/
                        todosLibros.add(datosLibro);

                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }

                session.setAttribute("libros", todosLibros);
                //session.setAttribute("Libros",conexion.obtenerLibros());

                /*GESTION BOTON CHECKOUT DE LA CESTA (CATALOGO.JSP) COMPRADOR*/
            } else if ("Checkout".equals(botonSeleccionado)) {
                ruta = PAGAR;

                /*GESTION BOTON PAGAR CESTA (PAGAR.JSP)COMPRADOR*/
            } else if ("Pagar".equals(botonSeleccionado)) {
                /*Obtenemos  de las variables de sesion el id del usuario, el total pagado, y su cesta para poder recogerlo en metodo
                al que llamamos mas abajo*/

                int idUsuario = Integer.parseInt((String) session.getAttribute("id_usuario"));
                double sumatorio = (double) session.getAttribute("sumatorio");
                Cesta cesta = (Cesta) session.getAttribute("cesta");

                /*Llamamos al metodo para insertar el pedido pagado del usuario */
                conexion.insertarPedido(idUsuario, sumatorio, cesta);

                /*Una vez que ha pulsado pagar en pago le lleva al menu */
                ruta = MENU;

                /*GESTION BOTON ESTADO PEDIDO (MENU.JSP) COMPRADOR*/
            } else if ("EstadoPedido".equals(botonSeleccionado)) {

                ruta = VER_PEDIDOS_COMPRADOR;

                /*GESTION BOTON DE FILTRAR PEDIDOS (VERPEDIDOS.JSP) COMPRADOR*/
            } else if ("Filtrar Pedido".equals(botonSeleccionado)) {
                /*Recojemos el id del usuario para pasarlo por parametro al metodo de buscar pedidos, para que solo nos muestre los pedidos
                de dicho usuario*/

                int idUsuario = Integer.parseInt((String) session.getAttribute("id_usuario"));

                /*Recojemos la opcion selecionada en el select, donde el usuario puede ver sus pedidos usando un filtro en cuanto al estado*/
                String opcionSelect = request.getParameter("opcion");

                ResultSet pedidos = conexion.verPedidoC(idUsuario, opcionSelect);

                request.setAttribute("pedidos", pedidos);

                ruta = VER_PEDIDOS_COMPRADOR;
            } //PREVISUALIZACIÓN
            else if ("Salir".equals(botonSeleccionado)) {
                ruta = LOGIN;
            } else if ("Cambiar Rol".equals(botonSeleccionado)) {
                int idUsuario = Integer.parseInt((String) session.getAttribute("id_usuario"));

                /*Llamamos al metodo para insertar el pedido pagado del usuario */
                boolean respuestaInsertarSolicitud = conexion.insertarSolicitud(idUsuario);
                if (!respuestaInsertarSolicitud) {
                    request.setAttribute("mensajeCambiarRol", "Su solicitud fue envida");
                }else{
                    request.setAttribute("mensajeCambiarRol", "Fallo, ya existe una solicitud pendiente");
                }
                ruta = PREVISUALIZACION;

            } //VENDEDOR
            else if ("verSolicitudes".equals(botonSeleccionado)) {
                obtenerSolicitudes(conexion, session);

                ruta = VER_SOLICTUDES;

            } else if ("Cambiar de Rol".equals(botonSeleccionado)) {

                String[] solicitudesSeleccionadas = request.getParameterValues("opciones");

                for (int i = 0; i < solicitudesSeleccionadas.length; i++) {
                    conexion.modificarRol(solicitudesSeleccionadas[i]);
                }
                obtenerSolicitudes(conexion, session);
                ruta = VER_SOLICTUDES;

            } else if ("Volver al menu".equals(botonSeleccionado)) {
                ruta = MENU;
                
            } else if ("Volver al Catalogo".equals(botonSeleccionado)){
                ruta = CATALOGO;
            }
            else if ("Cambiar Estados".equals(botonSeleccionado)) {
                //primera vez al entrar
                String filtro = request.getParameter("opcion");
                obtenerEstadoPedidos(conexion, session, filtro);
                ruta = VER_PEDIDOS_VENDEDOR;
            } else if ("Modificar Rol".equals(botonSeleccionado)) {
                //obtenemos el filtro actual
                String filtro = request.getParameter("opcion");
                //Obtenemos los dedidos marcados
                String[] pedidosSeleccionados = request.getParameterValues("opciones");
                //obtenemos el rol a cambiar
                String rol = request.getParameter("rol");
                for (int i = 0; i < pedidosSeleccionados.length; i++) {
                    conexion.modificarEstado(rol, pedidosSeleccionados[i]);
                }

                obtenerEstadoPedidos(conexion, session, filtro);
                ruta = VER_PEDIDOS_VENDEDOR;
            } else if ("Filtrar estado".equals(botonSeleccionado)) {
                String filtro = request.getParameter("opcion");
                obtenerEstadoPedidos(conexion, session, filtro);
                ruta = VER_PEDIDOS_VENDEDOR;
            }
            /*Redirigo la peticion */
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
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
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ServerControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    private void obtenerSolicitudes(Conexion conexion, HttpSession session) {
        ResultSet recogerSolicitudes = conexion.obtenerSolicitudesPendientes();
        /*Creo el arrayList de productos llamado solicitudes*/
        ArrayList<Solicitud> solicitudes = new ArrayList();

        try {
            /*Uso el metodo .next() para recoger los registros extraidos por resulset */
            while (recogerSolicitudes.next()) {
                String idSolicitud = recogerSolicitudes.getString("id_solicitud");
                String idUsuario = recogerSolicitudes.getString("id_usuario");
                String estado = recogerSolicitudes.getString("estado");
                Date fechaSolicitud = recogerSolicitudes.getDate("fecha_solicitud");
                /*Creo un nuevo Objeto producto con los datos correspondientes a cada libro de la BD */
                Solicitud solicitud = new Solicitud(idSolicitud, idUsuario, estado, fechaSolicitud, null);

                /*Añado al arrayList creado la solicitud creada con la informacion de los libros extraidos de la Bd*/
                solicitudes.add(solicitud);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        session.setAttribute("solicitudes", solicitudes);
    }

    private void obtenerEstadoPedidos(Conexion conexion, HttpSession session, String filtro) {

        ResultSet recogerEstadoPedidos = conexion.obtenerPedidos(filtro);
        /*Creo el arrayList de productos llamado solicitudes*/
        ArrayList<Pedido> estadoPedidos = new ArrayList();

        try {
            /*Uso el metodo .next() para recoger los registros extraidos por resulset */
            while (recogerEstadoPedidos.next()) {
                Integer idUsuario = recogerEstadoPedidos.getInt("pedido.id_usuario");
                Integer idPedido = recogerEstadoPedidos.getInt("pedido.id_pedido");
                String estado = recogerEstadoPedidos.getString("Estado");
                Double total = recogerEstadoPedidos.getDouble("TOTAL");
                Integer articulos = recogerEstadoPedidos.getInt("Articulos");
                /*Creo un nuevo Objeto estado pedido con los datos correspondientes a cada libro de la BD */
                DetallePedido detallePedido = new DetallePedido(null, null, null, articulos);
                Pedido estadoPedido = new Pedido(idUsuario, idPedido, estado, total, detallePedido);

                /*Añado al arrayList creado la solicitud creada con la informacion de los libros extraidos de la Bd*/
                estadoPedidos.add(estadoPedido);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        session.setAttribute("estadoPedidos", estadoPedidos);
    }

}
