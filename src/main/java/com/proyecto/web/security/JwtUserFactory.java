package com.proyecto.web.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

//import com.proyecto.web.model.Authority;
import com.proyecto.web.model.Usuario;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(Usuario user) {
        return new JwtUser(
                user.getIdusuario(),
                mapToGrantedAuthorities(new ArrayList<String>(Arrays.asList(user.getIdempleado().getPerfil().getDescPerfil()))),
                user.getIdempleado().getNombre().concat(" " + user.getIdempleado().getApPaterno()).concat(" " + user.getIdempleado().getApMaterno()),
                user.getUsername(),
                user.getPassword(),
                user.isEstado(),
                user.getUserreg(),
                user.getFechareg(),
                user.getUserupd(),
                user.getFechaupd(),
                user.getUserdes(),
                user.getLastpasswordresetdate()
        );
    }

//    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Perfil> authorities) {
//        return authorities.stream()
//                .map(authority -> new SimpleGrantedAuthority(authority.getDescPerfil()))
////                .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
//                .collect(Collectors.toList());
//    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities){
        return authorities.stream().map(Authority -> new SimpleGrantedAuthority(Authority)).collect(Collectors.toList());
    }

}
