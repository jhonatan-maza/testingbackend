package com.proyecto.web.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonRespuesta<T> {
	
	@JsonProperty("estado")
    private Integer estado;
    @JsonProperty("mensaje")
    private String mensaje;
    @JsonProperty("data")
    private List<T> data;
    @JsonProperty("item")
    private T item;
    
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
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public T getItem() {
		return item;
	}
	public void setItem(T item) {
		this.item = item;
	}

}
