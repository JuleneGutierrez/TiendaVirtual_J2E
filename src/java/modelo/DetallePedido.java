
package modelo;

import java.io.Serializable;


public class DetallePedido implements Serializable{
    private Integer idDetallePedido;
    private String tituloPedido;
    private Integer idPedido;
    private Integer cantidad;

    public DetallePedido() {
    }

    public DetallePedido(Integer idDetallePedido, String tituloPedido, Integer idPedido, Integer cantidad) {
        this.idDetallePedido = idDetallePedido;
        this.tituloPedido = tituloPedido;
        this.idPedido = idPedido;
        this.cantidad = cantidad;
    }

    public Integer getIdDetallePedido() {
        return idDetallePedido;
    }

    public void setIdDetallePedido(Integer idDetallePedido) {
        this.idDetallePedido = idDetallePedido;
    }

    public String getTituloPedido() {
        return tituloPedido;
    }

    public void setTituloPedido(String tituloPedido) {
        this.tituloPedido = tituloPedido;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
}
