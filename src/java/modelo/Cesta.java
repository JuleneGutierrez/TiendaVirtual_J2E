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



/**
 *
 * @author Julencia
 */
public class Cesta implements Serializable
{

    
    /*Atributo de la clase cesta, ArrayList*/
    
    private ArrayList<Producto> arrayProductos;

    /*Constructor vacio*/
    public Cesta()
    {
        this.arrayProductos = new ArrayList<Producto>();
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
    
    
    /*Metodos*/
    
    public void agregarProducto(Producto producto)
    {
        /*Usamos el metodo add de arrayList y le pasamos el producto*/
        this.arrayProductos.add(producto);
        
    }
   
    
      // Implementación del método serialize
    public byte[] serialize() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(this);
        objectOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    // Implementación del método unserialize
    public static Producto unserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Producto productos = (Producto) objectInputStream.readObject();
        objectInputStream.close();
        return productos;
    }
    
    /*Metodo para buscar El producto y despues modificar la cantidad del mismo que se quiere comprar*/
    
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
    
    /*CREAR EL METODO DE ELIMINAR*/
  
   
}
