package com.proyecto.web.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by jonat on 31/08/2019.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Operacion {

    @JsonProperty("idoperacion")
    private Integer idOperacion;

    @JsonProperty("descoperacion")
    private String descOperacion;

    @JsonProperty("estado")
    private String estado;

    public Integer getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(Integer idOperacion) {
        this.idOperacion = idOperacion;
    }

    public String getDescOperacion() {
        return descOperacion;
    }

    public void setDescOperacion(String descOperacion) {
        this.descOperacion = descOperacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
