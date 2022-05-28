package com.proyecto.web.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by jonat on 29/03/2020.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonRespuestaMenu {

    @JsonProperty("estado")
    private Integer estado;
    @JsonProperty("mensaje")
    private String mensaje;
    @JsonProperty("menu")
    private String menu;

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

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }
}
