package com.proyecto.web.dao;

import com.proyecto.web.model.Nivel;
import com.proyecto.web.model.Perfil;
import com.proyecto.web.util.RespuestaTransaccion;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.ReturningWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonat on 14/03/2020.
 */
@Repository
public class PerfilDaoImpl implements PerfilDao {

    private static final Logger log = LoggerFactory.getLogger(PerfilDaoImpl.class);
    @Autowired
    private SessionFactory sessionFactory;

    @Value("${paqueteConfiguracion}")
    protected String paqueteConfiguracion;

    @Transactional(readOnly = true)
    public List<Perfil> ListarPerfil(final Integer idNivel, final String Descripcion, final String Estado, final String PaginaStart, final String PaginaLength, final String Orderby) {
        return sessionFactory.getCurrentSession().doReturningWork(new ReturningWork<List<Perfil>>() {
            public List<Perfil> execute(Connection connection) throws SQLException {
                try {
                    CallableStatement cst = connection.prepareCall("{ ? = call "+paqueteConfiguracion+"SQL_PERFIL(?,?,?,?,?,?,?)}");
                    cst.registerOutParameter(1, Types.OTHER);
                    cst.setInt(2,0);
                    cst.setInt(3,idNivel);
                    cst.setString(4,Descripcion);
                    cst.setString(5,Estado);
                    cst.setString(6,PaginaStart);
                    cst.setString(7,PaginaLength);
                    cst.setString(8,Orderby);
                    cst.execute();
                    List<Perfil> lista = new ArrayList<Perfil>();
                    ResultSet rs = (ResultSet) cst.getObject(1);
                    if (rs != null) {
                        while (rs.next()) {
                            Perfil obj = new Perfil();
                            obj.setIdPerfil(rs.getInt(1));
                            obj.setDescPerfil(rs.getString(2));
                            obj.setEstado(rs.getString(3));
                            obj.setNivel(new Nivel());
                            obj.getNivel().setIdNivel(rs.getInt(4));
                            obj.getNivel().setDescNivel(rs.getString(5));
                            obj.setUsuariosAct(rs.getInt(6));
                            obj.setUsuariosInact(rs.getInt(7));
                            lista.add(obj);
                        }
                        rs.close();
                        cst.close();
                    }
                    return lista;
                }catch ( Exception e) {
                    log.debug("Ocurrio una excepcion "+e.getMessage());
                    return null;
                }
            }
        });
    }

    @Transactional(readOnly = true)
    public Integer CantidadPerfil(final Integer idNivel, final String Descripcion,final String Estado) {
        return  sessionFactory.getCurrentSession().doReturningWork(new ReturningWork<Integer>() {
            public Integer execute(Connection connection) throws SQLException {
                try{
                    CallableStatement cst = connection.prepareCall(" { ? = call "+paqueteConfiguracion+"SQL_PERFIL(?,?,?,?,?,?,?)}");
                    cst.registerOutParameter(1, Types.OTHER);
                    cst.setInt(2,0);
                    cst.setInt(3,idNivel);
                    cst.setString(3,Descripcion);
                    cst.setString(4,Estado);
                    cst.setString(5,null);
                    cst.setString(6,null);
                    cst.setString(7,null);
                    cst.execute();
                    Integer cantidad = 0;
                    ResultSet rs = (ResultSet) cst.getObject(1);
                    if (rs != null) {
                        while (rs.next()) {
                            cantidad = rs.getInt(1);
                        }
                        rs.close();
                        cst.close();
                    }
                    return cantidad;
                }catch ( Exception e) {
                    log.debug("Ocurrio una excepcion "+e.getMessage());
                    return null;
                }
            }
        });
    }

    @Transactional(readOnly = true)
    public Perfil viewPerfil(final Integer Codigo) {
        return sessionFactory.getCurrentSession().doReturningWork(new ReturningWork<Perfil>() {
            public Perfil execute(Connection connection) throws SQLException {
                try {
                    CallableStatement cst = connection.prepareCall("{ ? = call "+paqueteConfiguracion+"SQL_PERFIL(?,?,?,?,?,?,?)}");
                    cst.registerOutParameter(1, Types.OTHER);
                    cst.setInt(2,Codigo);
                    cst.setInt(3,0);
                    cst.setString(4,null);
                    cst.setString(5,null);
                    cst.setString(6,"0");
                    cst.setString(7,"1");
                    cst.setString(8,"1");
                    cst.execute();
                    Perfil perfil = null;
                    ResultSet rs = (ResultSet) cst.getObject(1);
                    if (rs != null) {
                        while (rs.next()) {
                            perfil = new Perfil();
                            perfil.setIdPerfil(rs.getInt(1));
                            perfil.setDescPerfil(rs.getString(2));
                            perfil.setEstado(rs.getString(3));
                            perfil.setNivel(new Nivel());
                            perfil.getNivel().setIdNivel(rs.getInt(4));
                        }
                        rs.close();
                        cst.close();
                    }
                    return perfil;
                }catch ( Exception e) {
                    log.debug("Ocurrio una excepcion "+e.getMessage());
                    return null;
                }
            }
        });
    }

    @Transactional(readOnly = false)
    public RespuestaTransaccion mantenimientoPerfil(final Perfil perfil, final String usuario, final Integer operacion) {
        return  sessionFactory.getCurrentSession().doReturningWork(new ReturningWork<RespuestaTransaccion>() {
            public RespuestaTransaccion execute(Connection connection) throws SQLException {
                RespuestaTransaccion respTransaccion = new RespuestaTransaccion();
                try{
                    CallableStatement cst = connection.prepareCall("{ ? = call "+paqueteConfiguracion+"IUD_PERFIL(?,?,?,?,?,?) }");
                    cst.registerOutParameter(1, Types.INTEGER);
                    cst.setInt(2, perfil.getIdPerfil());
                    cst.setInt(3, perfil.getNivel().getIdNivel());
                    cst.setString(4, perfil.getDescPerfil().toUpperCase());
                    cst.setString(5, perfil.getEstado());
                    cst.setString(6, usuario);
                    cst.setInt(7, operacion);
                    cst.execute();

                    Integer respuesta = cst.getInt(1);
                    if(respuesta.equals(1)){
                        respTransaccion.setEstado(1); //EXITO
                        respTransaccion.setMensaje("Éxito al guardar");
                        respTransaccion.setMensajeBaseDatos(null);
                    }
                    else if(respuesta.equals(2)){
                        respTransaccion.setEstado(1); //EXITO
                        respTransaccion.setMensaje("Éxito al actualizar");
                        respTransaccion.setMensajeBaseDatos(null);
                    }
                    else if(respuesta.equals(3)){
                        respTransaccion.setEstado(1); //EXITO
                        respTransaccion.setMensaje("Éxito al cambiar estado");
                        respTransaccion.setMensajeBaseDatos(null);
                    }
                    else if(respuesta.equals(100)){
                        respTransaccion.setEstado(5); //ADVERTENCIA
                        respTransaccion.setMensaje("Registro : ( "+ perfil.getDescPerfil().toUpperCase() +" ) ya existe");
                        respTransaccion.setMensajeBaseDatos(null);
                    }
                    else{
                        respTransaccion.setEstado(0); //ERROR
                        respTransaccion.setMensaje("Transacción Errónea");
                        respTransaccion.setMensajeBaseDatos("Transacción Errónea");
                    }
                    cst.close();
                    return respTransaccion;
                }catch ( SQLException e) {
                    log.debug("Ocurrio una excepcion "+e.getMessage());
                    respTransaccion.setEstado(0);
                    respTransaccion.setMensaje("Transacción Errónea");
                    respTransaccion.setMensajeBaseDatos(e.getMessage());
                    return respTransaccion;
                }
            }
        });
    }

}
