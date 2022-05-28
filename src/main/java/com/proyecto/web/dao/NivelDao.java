package com.proyecto.web.dao;

import com.proyecto.web.model.Nivel;
import com.proyecto.web.util.RespuestaTransaccion;

import java.util.List;

/**
 * Created by jonat on 14/03/2020.
 */
public interface NivelDao {

    List<Nivel> ListarNivel(String Descripcion, String Estado, String PaginaStart, String PaginaLength, String Orderby);
    Integer CantidadNivel(String Descripcion, String Estado);
    Nivel viewNivel(Integer Codigo);
    RespuestaTransaccion mantenimientoNivel(Nivel nivel, String usuario, Integer operacion);

}
