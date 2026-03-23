package com.anonimo.server.repository;

import java.util.List;

import com.anonimo.server.entity.UsuarioEntity;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<UsuarioEntity>{
	// Busca todos os usuários com um status específico
    public List<UsuarioEntity> listarUsuariosOnline() {
        return list("statusGlobal", "ON");
    }
}
