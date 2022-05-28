package com.proyecto.web.dao;

import com.proyecto.web.model.Perfil;
import com.proyecto.web.util.RespuestaTransaccion;

import java.util.List;

/**
 * Created by jonat on 14/03/2020.
 */
public interface PerfilDao {

    List<Perfil> ListarPerfil(Integer idNivel, String Descripcion, String Estado, String PaginaStart, String PaginaLength, String Orderby);
    Integer CantidadPerfil(Integer idNivel, String Descripcion, String Estado);
    Perfil viewPerfil(Integer Codigo);
    RespuestaTransaccion mantenimientoPerfil(Perfil perfil, String usuario, Integer operacion);

}
