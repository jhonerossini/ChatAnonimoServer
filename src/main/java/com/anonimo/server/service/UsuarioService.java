package com.anonimo.server.service;

import java.time.LocalDateTime;

import com.anonimo.server.dto.UsuarioRequestDTO;
import com.anonimo.server.entity.LoginEntity;
import com.anonimo.server.entity.UsuarioEntity;
import com.anonimo.server.repository.UsuarioRepository;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UsuarioService {
	
	@Inject
	UsuarioRepository usuarioRepository;

	@Transactional
	public UsuarioEntity criarUsuario(UsuarioRequestDTO dto) {
		
		UsuarioEntity usuario = new UsuarioEntity();
        
        // Transferindo os dados do DTO (Record) para a Entidade
        usuario.cpf = dto.getCpf();
        usuario.nome = dto.getNome();
        usuario.sobrenome = dto.getSobrenome();
        usuario.status = dto.getStatus();
        usuario.nick = dto.getNick();
        usuario.mensagem = dto.getMensagem();
        usuario.statusGlobal = dto.getStatusGlobal();
        usuario.usuarioCriacao = dto.getUsuarioCriacao();
        usuario.usuarioAtualizacao = dto.getUsuarioAtualizacao();
        
        LoginEntity login = new LoginEntity();
        login.login = dto.getLogin();
        login.senha = BcryptUtil.bcryptHash(dto.getSenha());
        login.role = "ADMIN";
        login.dataAtualizacao=LocalDateTime.now();
        login.dataCriacao=LocalDateTime.now();
        login.usuarioCriacao=dto.getLogin();
        login.usuarioAtualizacao=dto.getLogin();
        
        usuario.loginEntity = login;

        // Grava no banco de dados
        usuarioRepository.persistAndFlush(usuario);
        
        return usuario;
        
	}
}
