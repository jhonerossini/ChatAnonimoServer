package com.anonimo.server.service;

import com.anonimo.server.entity.UsuarioEntity;
import com.anonimo.server.repository.UsuarioRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ChatUsuarioService {

	@Inject
    UsuarioRepository usuarioRepository;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void registrarEntrada(String loginDoUsuario) {
        UsuarioEntity usuario = usuarioRepository.find("loginEntity.login", loginDoUsuario).firstResult();
        if (usuario != null) {
            usuario.statusGlobal = "ON";
            usuarioRepository.persistAndFlush(usuario);
            System.out.println(">>> Usuário ENTROU na sala (Transação Nova): " + loginDoUsuario);
        }
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void registrarSaida(String loginDoUsuario) {
        UsuarioEntity usuario = usuarioRepository.find("loginEntity.login", loginDoUsuario).firstResult();
        if (usuario != null) {
            usuario.statusGlobal = "OF";
            usuarioRepository.persistAndFlush(usuario);
            System.out.println("<<< Usuário SAIU da sala (Transação Nova): " + loginDoUsuario);
        }
    }
}
