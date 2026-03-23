package com.anonimo.server.dto;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class UserContext {

	private String usuario;
    private String role;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
