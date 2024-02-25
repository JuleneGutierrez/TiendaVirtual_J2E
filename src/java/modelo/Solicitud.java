
package modelo;

import java.io.Serializable;
import java.util.Date;


public class Solicitud implements Serializable{
    private String idSolicitud;
    private String idUsuario;
    private String estado;
    private Date fechaSolicitud;
    private Date fechaAprobacion;

    public Solicitud() {
    }

    public Solicitud(String idSolicitud, String idUsuario, String estado, Date fechaSolicitud, Date fechaAprobacion) {
        this.idSolicitud = idSolicitud;
        this.idUsuario = idUsuario;
        this.estado = estado;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaAprobacion = fechaAprobacion;
    }

 

    public String getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Date getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(Date fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

 
    
    
}
