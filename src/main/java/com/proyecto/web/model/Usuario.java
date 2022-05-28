package com.proyecto.web.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

/**
 * Created by jonat on 25/08/2019.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Usuario {

    private Integer idusuario;
    private Empleado idempleado;
    private String username;
    private String password;
    private boolean estado;
    private String userreg;
    private String fechareg;
    private String userupd;
    private String fechaupd;
    private String userdes;
    private Date lastpasswordresetdate;

    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public Empleado getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(Empleado idempleado) {
        this.idempleado = idempleado;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getUserreg() {
        return userreg;
    }

    public void setUserreg(String userreg) {
        this.userreg = userreg;
    }

    public String getFechareg() {
        return fechareg;
    }

    public void setFechareg(String fechareg) {
        this.fechareg = fechareg;
    }

    public String getUserupd() {
        return userupd;
    }

    public void setUserupd(String userupd) {
        this.userupd = userupd;
    }

    public String getFechaupd() {
        return fechaupd;
    }

    public void setFechaupd(String fechaupd) {
        this.fechaupd = fechaupd;
    }

    public String getUserdes() {
        return userdes;
    }

    public void setUserdes(String userdes) {
        this.userdes = userdes;
    }

    public Date getLastpasswordresetdate() {
        return lastpasswordresetdate;
    }

    public void setLastpasswordresetdate(Date lastpasswordresetdate) {
        this.lastpasswordresetdate = lastpasswordresetdate;
    }
}
