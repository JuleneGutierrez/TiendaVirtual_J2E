/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class Cesta implements Serializable
{

    /*Atributo de la clase cesta, ArrayList*/
    private ArrayList<Producto> arrayProductos;

    public Cesta()
    {
        this.arrayProductos = new ArrayList<>();
    }

    /*Constructor completo*/
    public Cesta(ArrayList<Producto> productos)
    {
        this.arrayProductos = productos;
    }

    /*GETTER Y SETTERS*/
    public ArrayList<Producto> getArrayProductos()
    {
        return arrayProductos;
    }

    public void setArrayProductos(ArrayList<Producto> arrayProductos)
    {
        this.arrayProductos = arrayProductos;
    }

    // Implementación del método serialize
    public byte[] serialize() throws IOException
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(this);
        objectOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    // Implementación del método unserialize
    public static Producto unserialize(byte[] data) throws IOException, ClassNotFoundException
    {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Producto productos = (Producto) objectInputStream.readObject();
        objectInputStream.close();
        return productos;
    }

    /*Metodo para buscar el producto dentro del arrayProductos, si lo encuentra devuelve el producto, sino devuelve un null*/
    public Producto buscarProducto(String tituloLibro)
    {
        /*Recorre el arraylist que contiene los arrayProductos*/
        for (int i = 0; i < this.arrayProductos.size(); i++)
        {
            /*Con este if comparamos el titulo que obtenemos al recorrer ese arraylist con el proporcionado*/
            if (this.arrayProductos.get(i).getTitulo().equals(tituloLibro))
            {
                /*Si encontramos coincidencia devolvemos el objeto producto*/
                return arrayProductos.get(i);
            }
        }

        return null;
    }

    /* Metodo usado para agregar nuevos productos a la cesta. Si ya existe el producto en la cesta, sumara las cantidades */
    public void agregarProductos(ArrayList<Producto> arrayNuevosProd)
    {
        /* Recorro el array con el bucle for */
        for (int i = 0; i < arrayNuevosProd.size(); i++)
        {
            /* Guardo en la variable producto lo que nos devuelve el metodo buscarProducto. 
                Al metodo le pasamos como parametro el titulo del producto que estamos mirando en el array. */
            
            /*Aqui metemos el libro encontrado por el metodo buscarProducto*/
            Producto productoCesta = buscarProducto(arrayNuevosProd.get(i).getTitulo());

            /* Si el metodo buscarProducto ha encontrado ese producto en la cesta, procedemos a sumar las cantidades */
            if (productoCesta != null)
            {
                /* Sumo la cantidad del producto que hemos recibido como parametro con la cantidad del producto de la cesta */
                int sumaCantidades = productoCesta.getCantidad() + arrayNuevosProd.get(i).getCantidad();

                /* Modificamos la cantidad del producto con la suma resultante */
                productoCesta.setCantidad(sumaCantidades);
            } else
            {
                /* Si no existe el producto en la cesta, usamos el metodo add del arrat para que se agrega a ella */
                this.arrayProductos.add(arrayNuevosProd.get(i));
            }
        }
    }

    /* Metodo usado para elminar un producto por su posicion en el array, el parametre es la posicion de la celda que vamos a borrar */
    public void borrarProducto(String celdaBorrar)
    {
        /* Borramos la celda con el metodo remove de la clase ArrayList, como parametro le enviamos la posicion de la celda */
        this.arrayProductos.remove(Integer.parseInt(celdaBorrar));
    }
}
