package com.proyecto.web.security;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class JwtUser implements UserDetails {

	private static final long serialVersionUID = 1215456216545L;

    private final Integer idUsuario;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String nombreUsuario;
	private final String username;
    private final String password;
	private final boolean enabled;
	private final String usuRegistro;
	private final String fechaRegistro;
	private final String usuUpdate;
	private final String fechaUpdate;
	private final String usuDesactiva;
    private final Date lastPasswordResetDate;

    public JwtUser(
            Integer idUsuario,
            Collection<? extends GrantedAuthority> authorities,
            String nombreUsuario,
            String username,
            String password,
            boolean enabled,
            String usuRegistro,
            String fechaRegistro,
            String usuUpdate,
            String fechaUpdate,
            String usuDesactiva,
            Date lastPasswordResetDate
    ) {
        this.idUsuario = idUsuario;
        this.authorities = authorities;
        this.nombreUsuario = nombreUsuario;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.usuRegistro = usuRegistro;
        this.fechaRegistro = fechaRegistro;
        this.usuUpdate = usuUpdate;
        this.fechaUpdate = fechaUpdate;
        this.usuDesactiva = usuDesactiva;
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    @JsonIgnore
    public Integer getIdUsuario() {
        return idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @JsonIgnore
    public String getFechaRegistro(){return fechaRegistro;}

    @JsonIgnore
    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }
}
