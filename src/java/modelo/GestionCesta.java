/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Julencia
 */
public class GestionCesta
{

    public GestionCesta()
    {
    }
    
    
    /*Metodo usado para recoger los productos enviados por parametro Map y devolvemos un array de productos*/
    public ArrayList<Producto> catalogoProducto(Map<String, String[]> parametros)
    {
        ArrayList<Producto> arrayProducto = new ArrayList<>();
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
                    String[] partesClave = clave.substring(9).split("\\|");
                    // Obtener el nombre del libro desde el nombre del parámetro
                    String nombreLibro = partesClave[0];
                    // Obtener el precio del libro desde el nombre del parámetro
                    String precioLibro = partesClave[1].replace("]", "").trim(); // No olvides eliminar el corchete final y trim para eliminar espacios en blanco adicionales
                    // Obtener la cantidad del libro del array de valores
                    int cantidad = Integer.parseInt(valores[0]);

                    if (cantidad > 0)
                    {
                        Producto nuevoProducto = new Producto(nombreLibro, Double.parseDouble(precioLibro), cantidad);

                        arrayProducto.add(nuevoProducto);
                    }
                }
            }
        }
        
        return arrayProducto;
    }
}
