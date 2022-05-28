package com.proyecto.web.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by jonat on 7/04/2020.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespuestaTransaccion {

    @JsonProperty("estado")
    private Integer estado;
    @JsonProperty("mensaje")
    private String mensaje;
//    @JsonIgnore
    @JsonProperty("mensajebd")
    private String mensajeBaseDatos;

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensajeBaseDatos() {
        return mensajeBaseDatos;
    }

    public void setMensajeBaseDatos(String mensajeBaseDatos) {
        this.mensajeBaseDatos = mensajeBaseDatos;
    }
}
