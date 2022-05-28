package com.proyecto.web.service;

import com.proyecto.web.dao.PerfilDao;
import com.proyecto.web.model.Perfil;
import com.proyecto.web.util.RespuestaTransaccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jonat on 14/03/2020.
 */
@Service
public class PerfilServiceImpl implements PerfilService {

    @Autowired
    private PerfilDao perfilDao;

    public List<Perfil> ListarPerfil(Integer idNivel, String Descripcion, String Estado, String PaginaStart, String PaginaLength, String Orderby){
        return perfilDao.ListarPerfil(idNivel,Descripcion,Estado,PaginaStart,PaginaLength,Orderby);
    }

    public Integer CantidadPerfil(Integer idNivel, String Descripcion, String Estado){
        return perfilDao.CantidadPerfil(idNivel,Descripcion,Estado);
    }

    public Perfil viewPerfil(Integer Codigo){
        return perfilDao.viewPerfil(Codigo);
    }

    public RespuestaTransaccion mantenimientoPerfil(Perfil perfil, String usuario, Integer operacion){
        return perfilDao.mantenimientoPerfil(perfil,usuario,operacion);
    }

}
