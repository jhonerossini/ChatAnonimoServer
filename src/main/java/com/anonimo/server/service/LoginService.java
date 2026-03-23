package com.anonimo.server.service;

import java.util.HashMap;
import java.util.Map;

import com.anonimo.server.dto.LoginRequestDTO;
import com.anonimo.server.entity.LoginEntity;
import com.anonimo.server.entity.UsuarioEntity;
import com.anonimo.server.filter.JwtService;
import com.anonimo.server.repository.LoginRepository;
import com.anonimo.server.repository.UsuarioRepository;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class LoginService {
	
	@Inject
	LoginRepository loginRepository;
	
	@Inject
	UsuarioRepository usuarioRepository;
	
	@Inject
    JwtService jwtService;
	
	@Transactional
	public Map<String, Object> logar(LoginRequestDTO loginRequest){
		//1. Buscar o usuário pelo login informado
    	LoginEntity login = loginRepository.findByLogin(loginRequest.getLogin());
    	
    	if(!BcryptUtil.matches(loginRequest.getSenha(), login.senha))
    		return null;
    	
    	// 2. Busca o Usuário associado a esse Login
        // O Panache consegue navegar pelos relacionamentos usando o ponto "."
        UsuarioEntity usuario = usuarioRepository.find("loginEntity.login", loginRequest.getLogin()).firstResult();

        if (usuario != null) {
            // 3. Muda o status para Online e o Hibernate atualiza no banco automaticamente
            usuario.statusGlobal = "ON"; 
            usuarioRepository.persist(usuario);
        }
    	
    	// 2. Gerar o token JWT
        String token = jwtService.gerarToken(usuario.loginEntity.login);
        
        Map<String, Object> retorno = new HashMap<>();
        retorno.put("token", token);
    	
    	return retorno;
	}
	
	public boolean deslogar(String login) {
		// Busca o usuário pelo login informado
        UsuarioEntity usuario = usuarioRepository.find("loginEntity.login", login).firstResult();
        
        if (usuario == null)
        	return false;
        
        // Muda o status para Offline
        usuario.statusGlobal = "OF";
        usuarioRepository.persist(usuario);
        
        return true;
	}
	

}
