package com.anonimo.server.repository;

import com.anonimo.server.entity.LoginEntity;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LoginRepository implements PanacheRepository<LoginEntity>{
	
    // --- Métodos de Busca (Active Record) ---

    public LoginEntity findByLogin(String login) {
        return find("login", login).firstResult();
    }

    public LoginEntity findByCpf(String cpf) {
        return find("cpf", cpf).firstResult();
    }
    
    
}
