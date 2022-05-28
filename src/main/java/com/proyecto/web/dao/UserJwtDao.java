package com.proyecto.web.dao;

import com.proyecto.web.model.Usuario;

import java.util.List;

/**
 * Created by jonat on 25/08/2019.
 */
public interface UserJwtDao {

    Usuario findByUsername(String username);

}
