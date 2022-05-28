package com.proyecto.web.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by jonat on 25/08/2019.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Perfil {

    @JsonProperty("idperfil")
    private Integer idPerfil;

    @JsonProperty("nivel")
    private Nivel nivel;

    @JsonProperty("descperfil")
    private String descPerfil;

    @JsonProperty("estado")
    private String estado;

    private Integer usuariosAct;

    private Integer usuariosInact;

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public String getDescPerfil() {
        return descPerfil;
    }

    public void setDescPerfil(String descPerfil) {
        this.descPerfil = descPerfil;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getUsuariosAct() {
        return usuariosAct;
    }

    public void setUsuariosAct(Integer usuariosAct) {
        this.usuariosAct = usuariosAct;
    }

    public Integer getUsuariosInact() {
        return usuariosInact;
    }

    public void setUsuariosInact(Integer usuariosInact) {
        this.usuariosInact = usuariosInact;
    }
}
