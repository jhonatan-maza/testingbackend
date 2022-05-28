package com.proyecto.web.service;

import com.proyecto.web.dao.NivelDao;
import com.proyecto.web.model.Nivel;
import com.proyecto.web.util.RespuestaTransaccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jonat on 14/03/2020.
 */
@Service
public class NivelServiceImpl implements NivelService {

    @Autowired
    private NivelDao nivelDao;

    public List<Nivel> ListarNivel(String Descripcion, String Estado, String PaginaStart, String PaginaLength, String Orderby){
        return nivelDao.ListarNivel(Descripcion,Estado,PaginaStart,PaginaLength,Orderby);
    }

    public Integer CantidadNivel(String Descripcion, String Estado){
        return nivelDao.CantidadNivel(Descripcion,Estado);
    }

    public Nivel viewNivel(Integer Codigo){
        return nivelDao.viewNivel(Codigo);
    }

    public RespuestaTransaccion mantenimientoNivel(Nivel nivel, String usuario, Integer operacion){
        return nivelDao.mantenimientoNivel(nivel,usuario,operacion);
    }
}
