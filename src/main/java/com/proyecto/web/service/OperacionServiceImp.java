package com.proyecto.web.service;

import com.proyecto.web.dao.OperacionDao;
import com.proyecto.web.model.Operacion;
import com.proyecto.web.util.RespuestaTransaccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jonat on 17/03/2020.
 */
@Service
public class OperacionServiceImp implements OperacionService {

    @Autowired
    private OperacionDao operacionDao;

    public List<Operacion> ListarOperacion(String Descripcion, String Estado, String PaginaStart, String PaginaLength, String Orderby){
        return operacionDao.ListarOperacion(Descripcion,Estado,PaginaStart,PaginaLength,Orderby);
    }

    public Integer CantidadOperacion(String Descripcion, String Estado){
        return operacionDao.CantidadOperacion(Descripcion,Estado);
    }

    public Operacion viewOperacion(Integer Codigo){
        return operacionDao.viewOperacion(Codigo);
    }

    public RespuestaTransaccion mantenimientoOperacion(Operacion operacion, String usuario, Integer nrOperacion){
        return operacionDao.mantenimientoOperacion(operacion,usuario,nrOperacion);
    }

}
