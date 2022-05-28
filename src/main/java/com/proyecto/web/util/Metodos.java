package com.proyecto.web.util;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jonat on 19/03/2020.
 */
public class Metodos {

    public static void LlenarDatosBD(ResultSet rs, Integer inicio, Integer fin, Object[] dato) throws SQLException {
        try {
            for (int i = inicio; i <= fin; i++) {
                dato[i - 1] = rs.getObject(i) != null ? rs.getString(i) : "0.00";
            }
        }catch (SQLException e){
            throw e;
        }
    }


}
