package com.proyecto.web.service;

import com.proyecto.web.model.Operacion;
import com.proyecto.web.util.RespuestaTransaccion;

import java.util.List;

/**
 * Created by jonat on 17/03/2020.
 */
public interface OperacionService {

    List<Operacion> ListarOperacion(String Descripcion, String Estado, String PaginaStart, String PaginaLength, String Orderby);
    Integer CantidadOperacion(String Descripcion, String Estado);
    Operacion viewOperacion(Integer Codigo);
    RespuestaTransaccion mantenimientoOperacion(Operacion operacion, String usuario, Integer nrOperacion);

}
