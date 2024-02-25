/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;

public class Pedido implements Serializable{

    private Integer idUsuario;
    private Integer idPedido;
    private String estadoPedido;
    private double facturado;
    private DetallePedido detallePedido;

    public Pedido(Integer idUsuario, Integer idPedido, String estadoPedido, double facturado, DetallePedido detallePedido) {
        this.idUsuario = idUsuario;
        this.idPedido = idPedido;
        this.estadoPedido = estadoPedido;
        this.facturado = facturado;
        this.detallePedido = detallePedido;
    }



   

    public Pedido() {
    }



    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }


    public double getFacturado() {
        return facturado;
    }

    public void setFacturado(double facturado) {
        this.facturado = facturado;
    }

    public DetallePedido getDetallePedido() {
        return detallePedido;
    }

    public void setDetallePedido(DetallePedido detallePedido) {
        this.detallePedido = detallePedido;
    }
    
    
    
            
            
}
