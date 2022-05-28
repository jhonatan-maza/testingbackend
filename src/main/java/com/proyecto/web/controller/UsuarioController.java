package com.proyecto.web.controller;

import com.proyecto.web.model.Usuario;
import com.proyecto.web.security.JwtTokenUtil;
import com.proyecto.web.service.UserJwtService;
//import com.proyecto.web.util.JsonRespuesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping(value="/app/usuario")
public class UsuarioController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserJwtService userJwtService;


}
