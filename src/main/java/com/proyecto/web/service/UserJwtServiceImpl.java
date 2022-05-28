package com.proyecto.web.service;

import com.proyecto.web.dao.UserJwtDao;
import com.proyecto.web.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jonat on 28/08/2019.
 */
@Service("UserJwtService")
public class UserJwtServiceImpl implements UserJwtService {

    @Autowired
    private UserJwtDao usuarioDao;
    public Usuario findByUsername(String username){
        return usuarioDao.findByUsername(username);
    }

}
