package com.proyecto.web.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by jonat on 18/08/2019.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Nivel {

    @JsonProperty("idnivel")
    private Integer idNivel;

    @JsonProperty("descnivel")
    private String descNivel;

    @JsonProperty("estado")
    private String estado;

    public Integer getIdNivel() {
        return idNivel;
    }

    public void setIdNivel(Integer idNivel) {
        this.idNivel = idNivel;
    }

    public String getDescNivel() {
        return descNivel;
    }

    public void setDescNivel(String descNivel) {
        this.descNivel = descNivel;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
