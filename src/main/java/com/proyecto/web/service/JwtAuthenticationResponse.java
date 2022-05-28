package com.proyecto.web.service;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;
    private final String usuarioToken;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String nombreUsuario;

    public JwtAuthenticationResponse(String token, String usuarioToken, Collection<? extends GrantedAuthority> authorities, String nombreUsuario) {
        this.token = token;
        this.usuarioToken = usuarioToken;
        this.authorities = authorities;
        this.nombreUsuario = nombreUsuario;
    }

    public String getAccessToken() {
        return this.token;
    }
    public String getUsername() {
        return usuarioToken;
    }
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    public String getNameuser() {
        return nombreUsuario;
    }
}
