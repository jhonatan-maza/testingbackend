package com.proyecto.web.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by jonat on 31/08/2019.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonRespuestaCant {

    @JsonProperty("cantidad")
    private Integer cantidad;

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
