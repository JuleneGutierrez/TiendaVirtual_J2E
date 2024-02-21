/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Julencia
 */
public class Producto
{
    private String titulo;
    private double precio;
    private int cantidad;
    private String autor;
    private String editorial;

    /*Constructor parcial  para uso en la cesta(modificacion de cantidad de producto a comprar)*/
    public Producto(String titulo, double precio, int cantidad)
    {
        this.titulo = titulo;
        this.precio = precio;
        this.cantidad = cantidad;
    }
    
    
    /*Constructor completo*/
    public Producto(String titulo, double precio, String autor, String editorial)
    {
        this.titulo = titulo;
        this.precio = precio;
        this.cantidad = 0;
        this.autor = autor;
        this.editorial = editorial;
    }

    /*Getters y setters*/
    public String getTitulo()
    {
        return titulo;
    }

    public void setTitulo(String titulo)
    {
        this.titulo = titulo;
    }

    public double getPrecio()
    {
        return precio;
    }

    public void setPrecio(double precio)
    {
        this.precio = precio;
    }

    public int getCantidad()
    {
        return cantidad;
    }

    public void setCantidad(int cantidad)
    {
        this.cantidad = cantidad;
    }

    public String getAutor()
    {
        return autor;
    }

    public void setAutor(String autor)
    {
        this.autor = autor;
    }

    public String getEditorial()
    {
        return editorial;
    }

    public void setEditorial(String editorial)
    {
        this.editorial = editorial;
    }
    
    
    
    
    
    
    
    
    
}
